import {useState} from 'react';
import axios, {AxiosRequestConfig} from 'axios';
import {useRecoilState} from "recoil";
import {delCookie} from '../Util/cookieUtil';
import {periodicRefresh} from '../Util/refreshToken';
import loadingAtom from "../Recoil/Atom/loadingAtom";

type AxiosProps = {
	method: 'get' | 'post' | 'put' | 'delete';
	url: string;
	data?: unknown;
	config?: AxiosRequestConfig;
};

// axios.defaults.baseURL = 'https://moviethree.synology.me/api';
axios.defaults.baseURL = 'http://localhost:8080/api';
axios.defaults.headers.withCredentials = true;

const useAxios = <T, >({
	                       method = 'get',
	                       url,
	                       data,
	                       config,
                       }: AxiosProps): [
	{
		response: T | undefined;
	},
	() => void,
] => {
	const [response, setResponse] = useState<T | undefined>();
	const [loading, setLoading] = useRecoilState(loadingAtom);
	
	const execution = () => {
		setLoading(loading + 1);
		
		if (method === 'get' || method === 'delete') {
			axios[method](url, config)
				.then((res) => {
					setResponse(res.data);
				})
				.catch((err) => {
					alert(err.response.data.message);
					console.log(err);
				})
				.finally(() => {
					setLoading(loading - 1);
				});
		} else {
			axios[method](url, data, config)
				.then((res) => {
					setResponse(res.data);
				})
				.catch((err) => {
					const checkErr = err.response.data;
					if (
						(checkErr.status === 500 &&
							(checkErr.data.message === '만료된 토큰 입니다.' ||
								checkErr.data.message === '잘못된 토큰 입니다.')) ||
						(checkErr.status === 403 && checkErr.message === 'Access Denied')
					) {
						delCookie('AccessToken');
						delCookie('NickName');
						clearTimeout(periodicRefresh());
						localStorage.clear();
						window.location.href = '/main';
					} else alert(err.response.data.message);
				})
				.finally(() => {
					setLoading(loading - 1);
				});
		}
	};
	
	return [{response}, execution];
};

export default useAxios;

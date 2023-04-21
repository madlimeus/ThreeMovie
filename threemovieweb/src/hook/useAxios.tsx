import { useState } from 'react';
import axios, { AxiosError, AxiosRequestConfig } from 'axios';
import { delCookie } from '../Util/cookieUtil';
import { periodicRefresh } from '../Util/refreshToken';

type AxiosProps = {
    method: 'get' | 'post' | 'put' | 'delete';
    url: string;
    data?: unknown;
    config?: AxiosRequestConfig;
};

axios.defaults.baseURL = 'http://moviethree.synology.me:8081/api';
axios.defaults.headers.withCredentials = true;

const useAxios = <T,>({
    method = 'get',
    url,
    data,
    config,
}: AxiosProps): [
    {
        response: any;
        error: string | AxiosError;
        loading: boolean;
    },
    () => void,
] => {
    const [response, setResponse] = useState<any>();
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    const execution = () => {
        setLoading(true);

        if (method === 'get' || method === 'delete') {
            axios[method](url, config)
                .then((res) => {
                    setResponse(res.data);
                })
                .catch((err) => {
                    alert(err.response.data);
                    setError(err);
                })
                .finally(() => {
                    setLoading(false);
                });
        } else {
            axios[method](url, data, config)
                .then((res) => {
                    setResponse(res);
                })
                .catch((err) => {
                    const checkErr = err.response.data;
                    console.log(checkErr);
                    if (
                        (checkErr.status === 500 &&
                            (checkErr.message === '만료된 토큰 입니다.' ||
                                checkErr.message === '잘못된 토큰 입니다.')) ||
                        (checkErr.status === 403 && checkErr.message === 'Access Denied')
                    ) {
                        console.log('통과');
                        delCookie('AccessToken');
                        delCookie('NickName');
                        clearTimeout(periodicRefresh());
                        localStorage.clear();
                        window.location.href = '/main';
                    } else if (err.response.data.message) alert(err.response.data.message);
                    else alert(err.response.data);
                    setError(err);
                })
                .finally(() => {
                    setLoading(false);
                });
        }
    };

    return [{ response, error, loading }, execution];
};

export default useAxios;

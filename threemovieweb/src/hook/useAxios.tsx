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

axios.defaults.baseURL = 'https://moviethree.synology.me:8080/api';
axios.defaults.headers.withCredentials = true;

const useAxios = <T,>({
    method = 'get',
    url,
    data,
    config,
}: AxiosProps): [
    {
        response: T | undefined;
        error: string | AxiosError;
        loading: boolean;
    },
    () => void,
] => {
    const [response, setResponse] = useState<T | undefined>();
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
                    setResponse(res.data);
                })
                .catch((err) => {
                    const checkErr = err.response.data;
                    if (
                        (checkErr.status === 500 &&
                            (checkErr.message === '만료된 토큰 입니다.' ||
                                checkErr.message === '잘못된 토큰 입니다.')) ||
                        (checkErr.status === 403 && checkErr.message === 'Access Denied')
                    ) {
                        delCookie('AccessToken');
                        delCookie('NickName');
                        clearTimeout(periodicRefresh());
                        localStorage.clear();
                        window.location.href = '/main';
                    } else if (typeof err.response.data === 'string') alert(err.response.data);
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

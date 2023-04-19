import { useState } from 'react';
import axios, { AxiosError, AxiosRequestConfig } from 'axios';

axios.defaults.baseURL = 'http://localhost:8080';
axios.defaults.withCredentials = true;

type AxiosProps = {
    method: 'get' | 'post' | 'put' | 'delete';
    url: string;
    data?: unknown;
    config?: AxiosRequestConfig;
};

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
                    console.log(err);
                    if (err.response.data.message) alert(err.response.data.message);
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

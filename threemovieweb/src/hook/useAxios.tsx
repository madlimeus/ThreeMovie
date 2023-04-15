import { useState } from 'react';
import axios, { AxiosRequestConfig } from 'axios';
import qs from 'qs';

axios.defaults.baseURL = 'http://localhost:8080';

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
        response: T | undefined;
        error: string;
        loading: boolean;
    },
    () => void,
] => {
    const [response, setResponse] = useState<T>();
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
                    setError(err);
                })
                .finally(() => {
                    setLoading(false);
                });
        } else {
            axios[method](url, qs.stringify(data), config)
                .then((res) => {
                    setResponse(res.data);
                })
                .catch((err) => {
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

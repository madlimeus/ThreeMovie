import React from 'react';
import { Box } from '@mui/material';
import QueryString from 'qs';
import { useLocation } from 'react-router-dom';
import useAxios from '../../assets/hook/useAxios';
import MovieDetail from '../../interfaces/MovieDetail';
import Loading from '../Loading';

const MovieDetailPage = () => {
    const location = useLocation();
    const queryData = QueryString.parse(location.search, { ignoreQueryPrefix: true });
    const { response, loading, error } = useAxios<MovieDetail>({
        method: 'get',
        url: `/api/movieinfo/moviedetail/${queryData.movie}`,
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    if (loading) <Loading />;
    return <Box className="detailcover">{response?.steelcuts && response?.trailer}</Box>;
};

export default MovieDetailPage;

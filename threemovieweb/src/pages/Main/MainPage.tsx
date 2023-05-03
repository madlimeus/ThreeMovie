import React, { useEffect } from 'react';
import { Box } from '@mui/material';
import { useSetRecoilState } from 'recoil';
import useAxios from '../../hook/useAxios';
import MovieData from '../../interfaces/MovieData';
import movieListAtom from '../../Recoil/Atom/movieLIstAtom';
import movieNowAtom from '../../Recoil/Atom/movieNowAtom';
import '../../style/scss/_mainpage.scss';
import Loading from '../Loading';
import MainMovieList from './MainMovieList';
import MainPreview from './MainPreview';

const MainPage = () => {
    const setMovieList = useSetRecoilState(movieListAtom);
    const setMovieNow = useSetRecoilState(movieNowAtom);
    const [{ response, loading, error }, refetch] = useAxios<MovieData[]>({
        method: 'get',
        url: '/movieinfo/movielist',
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    useEffect(() => {
        refetch();
    }, []);

    useEffect(() => {
        if (response && Array.isArray(response) && response[0]) {
            setMovieList(response);
            setMovieNow(response[0].movieId);
        }
    }, [response]);

    useEffect(() => {
        if (error) alert(error);
    }, [error]);

    if (loading) return <Loading />;
    return (
        <Box className="flexbox">
            <MainPreview />
            <MainMovieList />
        </Box>
    );
};

export default MainPage;

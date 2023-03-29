import React, { useEffect } from 'react';
import { Box } from '@mui/material';
import { useSetRecoilState } from 'recoil';
import useAxios from '../../assets/hook/useAxios';
import Movieinfo from '../../interfaces/Movieinfo';
import movieListAtom from '../../Recoil/Atom/movieLIstAtom';
import movieNowAtom from '../../Recoil/Atom/movieNowAtom';
import '../../style/scss/_mainpage.scss';
import Loading from '../Loading';
import MainMovieList from './MainMovieList';
import MainPreview from './MainPreview';

const MainPage = () => {
    const setMovieList = useSetRecoilState(movieListAtom);
    const setMovieNow = useSetRecoilState(movieNowAtom);
    const { response, loading, error } = useAxios<Movieinfo[]>({
        method: 'get',
        url: '/api/movieinfo/movielist',
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    useEffect(() => {
        if (response && Array.isArray(response)) {
            setMovieList(response);
            setMovieNow(response[0].movieId);
        }
    }, [response]);

    if (loading) return <Loading />;
    return (
        <Box className="flexbox">
            <MainPreview />
            <MainMovieList />
        </Box>
    );
};

export default MainPage;

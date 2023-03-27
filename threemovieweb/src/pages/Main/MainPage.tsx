import React, { useEffect } from 'react';
import { Box } from '@mui/material';
import { useRecoilState } from 'recoil';
import useAxios from '../../assets/hook/useAxios';
import Movieinfo from '../../interfaces/Movieinfo';
import movieListAtom from '../../Recoil/Atom/movieLIstAtom';
import '../../style/scss/_mainpage.scss';
import Loading from '../Loading';
import MainMovieList from './MainMovieList';
import MainPreview from './MainPreview';

const MainPage = () => {
    const [movieList, setMovieList] = useRecoilState(movieListAtom);
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

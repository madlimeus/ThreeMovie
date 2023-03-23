import React from 'react';
import { Box } from '@mui/material';
import MainPreview from './MainPreview';
import MainMovieList from './MainMovieList';
import '../../style/scss/_mainpage.scss';

const MainPage = () => {
    return (
        <Box className="flexbox">
            <MainPreview />
            <MainMovieList />
        </Box>
    );
};

export default MainPage;

import React from 'react';
import '../style/scss/_loading.scss';
import { Box, Typography } from '@mui/material';
import Spinner from '../assets/icons/spinner.gif';

const Loading = () => {
    return (
        <Box className="background">
            <Typography className="loadingText">로딩 중 입니다.</Typography>
            <img src={Spinner} alt="로딩" width="5%" />
        </Box>
    );
};

export default Loading;

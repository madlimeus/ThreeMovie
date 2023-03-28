import React from 'react';
import { Box, Button, Divider, Typography } from '@mui/material';

const NavMenu = () => {
    const onClickMain = () => {
        document.location.href = `/main`;
    };

    const onClickMovieList = () => {
        document.location.href = `/main`;
    };

    const onClickTheaterList = () => {
        document.location.href = `/main`;
    };

    const onClickBookinfo = () => {
        document.location.href = `/bookinfo`;
    };
    return (
        <Box className="navMenu">
            <Button onClick={onClickMain}>
                <Typography>메인</Typography>
            </Button>
            <Divider className="divide" orientation="vertical" variant="middle" flexItem />

            <Button onClick={onClickMovieList}>
                <Typography>영화 목록</Typography>
            </Button>
            <Divider className="divide" orientation="vertical" variant="middle" flexItem />

            <Button onClick={onClickTheaterList}>
                <Typography>영화관 목록</Typography>
            </Button>
            <Divider className="divide" orientation="vertical" variant="middle" flexItem />

            <Button onClick={onClickBookinfo}>
                <Typography>예약하기</Typography>
            </Button>
        </Box>
    );
};

export default NavMenu;

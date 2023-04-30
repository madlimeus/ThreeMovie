import React from 'react';
import { Box, Button, Divider, Typography } from '@mui/material';
import { locateBookInfo, locateMain, locateMovie } from '../../Util/locateUtil';

const NavMenu = () => {
    const onClickTheaterList = () => {
        document.location.href = `/main`;
    };

    return (
        <Box className="navMenu">
            <Button onClick={locateMain}>
                <Typography>메인</Typography>
            </Button>
            <Divider className="divide" orientation="vertical" variant="middle" flexItem />

            <Button onClick={() => locateMovie()}>
                <Typography>영화 목록</Typography>
            </Button>
            <Divider className="divide" orientation="vertical" variant="middle" flexItem />

            <Button onClick={onClickTheaterList}>
                <Typography>영화관 목록</Typography>
            </Button>
            <Divider className="divide" orientation="vertical" variant="middle" flexItem />

            <Button onClick={() => locateBookInfo()}>
                <Typography>예약하기</Typography>
            </Button>
        </Box>
    );
};

export default NavMenu;

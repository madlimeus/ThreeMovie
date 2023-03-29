import React from 'react';
import SearchIcon from '@mui/icons-material/Search';
import { Box, Button, Typography } from '@mui/material';
import Logo from '../../assets/images/MTLogo.png';

const NavHeader = () => {
    const onClickMain = () => {
        document.location.href = `/main`;
    };

    const onClickLogin = () => {
        document.location.href = `/login`;
    };

    return (
        <Box className="headmenu">
            <Button className="searchArea" endIcon={<SearchIcon />} disableRipple />
            <Button className="logo" onClick={onClickMain} disableElevation disableRipple>
                <img src={Logo} alt="" />
            </Button>
            <Button className="account" onClick={onClickLogin}>
                <Typography>로그인</Typography>
            </Button>
        </Box>
    );
};

export default NavHeader;

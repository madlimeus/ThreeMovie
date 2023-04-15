import React from 'react';
import { Box, Divider } from '@mui/material';
import { Outlet, useLocation } from 'react-router-dom';
import '../../style/scss/_flexbox.scss';
import '../../style/scss/_navbar.scss';
import NavHeader from './NavHeader';
import NavMenu from './NavMenu';

const NavBarPage = () => {
    const location = useLocation();

    return (
        <Box className="flexbox">
            {location.pathname.search('user') !== -1 && <Box className="backgroundImage" />}

            <Box className="coverHeader">
                <NavHeader />
                <Divider className="divide" variant="middle" />
                <NavMenu />
            </Box>
            <Outlet />
        </Box>
    );
};

export default NavBarPage;

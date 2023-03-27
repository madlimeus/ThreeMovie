import React from 'react';
import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';
import '../../style/scss/_flexbox.scss';

const NavBarPage = () => {
    return (
        <Box className="flexbox">
            <Box
                sx={{
                    minWidth: '1080px',
                    height: '70px',
                    width: '60vw',
                    backgroundColor: 'blue',
                }}
            />
            <Outlet />
        </Box>
    );
};

export default NavBarPage;

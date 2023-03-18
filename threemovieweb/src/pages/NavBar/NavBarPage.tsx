import React from 'react';
import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';

const NavBarPage = () => {
    return (
        <>
            <Box
                sx={{
                    height: '70px',
                    width: '60vw',
                    backgroundColor: 'blue',
                }}
            />
            <Outlet />
        </>
    );
};

export default NavBarPage;

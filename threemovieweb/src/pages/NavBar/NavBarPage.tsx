import React from 'react';
import { Box } from '@mui/material';
import { Outlet } from 'react-router-dom';

const NavBarPage = () => {
    return (
        <>
            <Box
                sx={{
                    height: '200px',
                    width: '700px',
                    backgroundColor: 'blue',
                }}
            />
            <Outlet />
        </>
    );
};

export default NavBarPage;

import React from 'react';
import { ThemeProvider } from '@mui/material';
import AppRouter from './Routes/AppRouter';
import typotheme from './style/typotheme';

const App = () => {
    return (
        <ThemeProvider theme={typotheme}>
            <AppRouter />
        </ThemeProvider>
    );
};

export default App;

import React from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import NavBarPage from '../pages/NavBar/NavBarPage';
import MainPage from '../pages/Main/MainPage';
import BookInfoPage from '../pages/Detail/BookInfo/BookInfoPage';
import SearchResultPage from '../pages/SearchResult/SearchResultPage';
import DetailPage from '../pages/Detail/DetailPage';
import LoginPage from '../pages/User/Login/LoginPage';
import SignUpPage from '../pages/User/SignUp/SignUpPage';
import GlobalStyles from '../style/global';

const AppRouter = () => {
    return (
        <>
            <GlobalStyles />
            <BrowserRouter>
                <Routes>
                    <Route element={<NavBarPage />}>
                        <Route path="/" element={<Navigate replace to="/main" />} />
                        <Route path="/main/*" element={<MainPage />} />
                        <Route path="/BookInfo/*" element={<BookInfoPage />} />
                        <Route path="/SearchResult/*" element={<SearchResultPage />} />
                    </Route>

                    <Route path="/Detail/*" element={<DetailPage />} />
                    <Route path="/login/*" element={<LoginPage />} />
                    <Route path="/SignUp/*" element={<SignUpPage />} />
                </Routes>
            </BrowserRouter>
        </>
    );
};

export default AppRouter;

import React from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import BookInfoPage from '../pages/BookInfo/BookInfoPage';
import MainPage from '../pages/Main/MainPage';
import MovieDetailPage from '../pages/MovieDetail/MovieDetailPage';
import NavBarPage from '../pages/NavBar/NavBarPage';
import SearchResultPage from '../pages/SearchResult/SearchResultPage';
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
                        <Route path="/bookInfo/*" element={<BookInfoPage />} />
                        <Route path="/moviedetail/*" element={<MovieDetailPage />} />
                        <Route path="/searchresult/*" element={<SearchResultPage />} />
                    </Route>
                    <Route path="/login/*" element={<LoginPage />} />
                    <Route path="/signup/*" element={<SignUpPage />} />
                </Routes>
            </BrowserRouter>
        </>
    );
};

export default AppRouter;

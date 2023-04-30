import React from 'react';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import NavBarPage from '../pages/NavBar/NavBarPage';
import GlobalStyles from '../style/global';
import Main from './Main/Main';
import BookInfo from './Detail/BookInfo/BookInfo';
import MovieDetail from './Detail/MovieDetail';
import SearchResult from './SearchResult/SearchResult';
import Login from './User/Login/Login';
import SignUp from './User/SignUp/SignUp';
import FindPass from './User/Find/FindPass';
import MyInfo from './User/MyInfo/MyInfo';
import Movie from './Movie/Movie';

const AppRouter = () => {
    return (
        <>
            <GlobalStyles />
            <BrowserRouter>
                <Routes>
                    <Route element={<NavBarPage />}>
                        <Route path="/" element={<Navigate replace to="/main" />} />
                        <Route path="/main/*" element={<Main />} />
                        <Route path="/bookInfo/*" element={<BookInfo />} />
                        <Route path="/moviedetail/*" element={<MovieDetail />} />
                        <Route path="/searchresult/*" element={<SearchResult />} />
                        <Route path="/user/login/*" element={<Login />} />
                        <Route path="/user/signup/*" element={<SignUp />} />
                        <Route path="/user/find/pass" element={<FindPass />} />
                        <Route path="/user/mypage/*" element={<MyInfo />} />
                        <Route path="movie/*" element={<Movie />} />
                    </Route>
                </Routes>
            </BrowserRouter>
        </>
    );
};

export default AppRouter;

import React from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import Main from './Main/Main';
import Detail from './Detail/Detail';
import Login from './User/Login/Login';
import BookInfo from './Detail/BookInfo/BookInfo';
import SearchResult from './SearchResult/SearchResult';
import SignUp from './User/SignUp/SignUp';

const AppRouter = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/main/*" element={<Main />} />
                <Route path="/Detail/*" element={<Detail />} />
                <Route path="/BookInfo/*" element={<BookInfo />} />
                <Route path="/SearchResult/*" element={<SearchResult />} />
                <Route path="/login/*" element={<Login />} />
                <Route path="/SignUp/*" element={<SignUp />} />
                <Route path="/" element={<Navigate replace to="/main" />} />
            </Routes>
        </BrowserRouter>
    );
};

export default AppRouter;

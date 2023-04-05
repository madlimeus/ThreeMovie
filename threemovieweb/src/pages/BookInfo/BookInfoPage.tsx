import React from 'react';
import {Box} from '@mui/material';
import QueryString from "qs";
import {useLocation} from "react-router-dom";

const BookInfoPage = () => {
	const location = useLocation();
	const queryData = QueryString.parse(location.search, {ignoreQueryPrefix: true});
	
	return <Box/>;
};

export default BookInfoPage;

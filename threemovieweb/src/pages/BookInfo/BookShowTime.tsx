import React from 'react';
import { Box } from '@mui/material';
import { useLazyQuery } from '@apollo/react-hooks';
import Loading from '../Loading';
import { GET_SHOW_TIME_LIST } from '../../gql/showtime.gql';

const BookShowTime = () => {
    const [onGetShowTime, { loading, error, data }] = useLazyQuery(GET_SHOW_TIME_LIST);

    if (loading) return <Loading />;
    return <Box />;
};

export default BookShowTime;

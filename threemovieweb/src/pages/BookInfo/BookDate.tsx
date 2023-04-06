import React from 'react';
import { Box } from '@mui/material';
import { useLazyQuery } from '@apollo/react-hooks';
import Loading from '../Loading';
import { GET_DATE_LIST } from '../../gql/showtime.gql';

const BookDate = () => {
    const [onGetDate, { loading, error, data }] = useLazyQuery(GET_DATE_LIST);

    if (loading) return <Loading />;
    return <Box />;
};

export default BookDate;

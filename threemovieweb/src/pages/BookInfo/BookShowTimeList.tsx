import React, { useEffect, useState } from 'react';
import { Box, Button, Divider, Typography } from '@mui/material';
import { useQuery } from '@apollo/react-hooks';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import Loading from '../Loading';
import { GET_SHOW_TIME_LIST } from '../../gql/showtime.gql';
import bookMovieFilterAtom from '../../Recoil/Atom/bookMovieFilterAtom';
import bookBrchFilterAtom from '../../Recoil/Atom/bookBrchFilterAtom';
import bookDateFilterAtom from '../../Recoil/Atom/bookDateFilterAtom';
import { BookShowTime } from '../../interfaces/BookShowTime';
import CGV from '../../assets/images/CGV.png';
import LC from '../../assets/images/LotteCinema.png';
import MB from '../../assets/images/MegaBox.png';
import bookAddrAtom from '../../Recoil/Atom/bookAddrAtom';
import bookTicketPageAtom from '../../Recoil/Atom/bookTicketPageAtom';
import retFilter from './retFilter';

const BookShowTimeList = () => {
    const setSelectAddr = useSetRecoilState(bookAddrAtom);
    const [selectTicketPage, setSelectTicketPage] = useRecoilState(bookTicketPageAtom);
    const movieFilter = useRecoilValue(bookMovieFilterAtom);
    const brchFilter = useRecoilValue(bookBrchFilterAtom);
    const dateFilter = useRecoilValue(bookDateFilterAtom);
    const [showTimeList, setShowTimeList] = useState<BookShowTime[]>([]);
    const { loading, error, data, refetch } = useQuery(GET_SHOW_TIME_LIST, {
        variables: { filter: retFilter() },
    });

    const onTheaterImg = (movieTheater: string) => {
        if (movieTheater === 'CGV') return CGV;
        if (movieTheater === 'LC') return LC;
        return MB;
    };

    const chkDate = (startTime: string) => {
        const now = new Date();
        const chkTime = new Date(`${dateFilter[0]} ${startTime}`);

        return now < chkTime;
    };

    const onClickShowTime = (addr: string, ticket: string) => {
        setSelectAddr(addr);
        setSelectTicketPage(ticket);
    };

    useEffect(() => {
        if (data) setShowTimeList(data.getShowTimeList);
    }, [data]);

    useEffect(() => {
        if (movieFilter.length > 0 && brchFilter.length > 0 && dateFilter.length > 0) refetch();
    }, [movieFilter, brchFilter, dateFilter]);

    if (loading) return <Loading />;
    return (
        <Box className="showTimeListCover">
            {showTimeList &&
                showTimeList.map((showTime) => (
                    <Box key={`${showTime.movieKR}${showTime.brchKR}${showTime.screenKR}`} className="showTimeBox">
                        <Box className="movieBrchCover">
                            <Box className="movieBrchName">
                                <Typography className="movieTitle">{showTime.movieKR}</Typography>
                                <Typography className="brchName">{showTime.brchKR}</Typography>
                                <img
                                    className="theaterImg"
                                    src={onTheaterImg(showTime.movieTheater)}
                                    alt={showTime.movieTheater}
                                />
                            </Box>

                            <Box className="nameImg">
                                <Typography>
                                    {showTime.playKind} | {showTime.screenKR} | {showTime.totalSeat}명
                                </Typography>
                            </Box>
                        </Box>
                        <Box className="showTimeCover">
                            {showTime.items
                                .filter((item) => chkDate(item.startTime))
                                .map((item) => (
                                    <Box key={item.ticketPage}>
                                        <Button
                                            onClick={() => onClickShowTime(showTime.addrKR, item.ticketPage)}
                                            className={
                                                item.ticketPage === selectTicketPage
                                                    ? 'active showTimeButton'
                                                    : 'showTimeButton'
                                            }
                                        >
                                            <Typography>{item.startTime}</Typography>
                                            <Typography>{item.restSeat}명</Typography>
                                        </Button>
                                    </Box>
                                ))}
                        </Box>
                        <Divider className="divide" />
                    </Box>
                ))}
        </Box>
    );
};

export default BookShowTimeList;

import React, { useEffect, useState } from 'react';
import { Box, Button, Typography } from '@mui/material';
import { useQuery } from '@apollo/react-hooks';
import { useRecoilState, useRecoilValue } from 'recoil';
import { Swiper, SwiperSlide } from 'swiper/react';
import SwiperCore, { Navigation } from 'swiper';
import Loading from '../Loading';
import { GET_DATE_LIST } from '../../gql/showtime.gql';
import bookMovieFilterAtom from '../../Recoil/Atom/bookMovieFilterAtom';
import bookBrchFilterAtom from '../../Recoil/Atom/bookBrchFilterAtom';
import bookDateFilterAtom from '../../Recoil/Atom/bookDateFilterAtom';
import { BookDate } from '../../interfaces/BookDate';
import retFilter from '../../Util/retFilter';

import 'swiper/scss';
import 'swiper/scss/navigation';

const BookDateList = () => {
    const movieFilter = useRecoilValue(bookMovieFilterAtom);
    const brchFilter = useRecoilValue(bookBrchFilterAtom);
    const [dateFilter, setDateFilter] = useRecoilState(bookDateFilterAtom);
    const [dateList, setDateList] = useState<BookDate[]>([]);
    const week = ['일', '월', '화', '수', '목', '금', '토'];
    const { loading, error, data, refetch } = useQuery(GET_DATE_LIST, {
        variables: { filter: retFilter() },
    });

    const onClickDate = (date: string) => {
        setDateFilter([date]);
    };

    const retWeek = (date: string) => {
        const when = new Date(date);
        return week[when.getDay()];
    };

    const retWeekClass = (date: string) => {
        let className = 'dateButton';
        const dateWeek = retWeek(date);
        if (dateWeek === '토') className += ' sat';
        else if (dateWeek === '일') className += ' sun';
        if (dateFilter[0] === date) className += ' active';
        return className;
    };

    const retWeekFilterClass = (date: string) => {
        let className = 'selectDate';
        const dateWeek = retWeek(date);
        if (dateWeek === '토') className += ' sat';
        else if (dateWeek === '일') className += ' sun';
        return className;
    };

    useEffect(() => {
        refetch();
    }, [movieFilter, brchFilter]);

    useEffect(() => {
        if (data) {
            setDateList(data.getDateList);
        }
    }, [data]);

    SwiperCore.use([Navigation]);
    if (loading) return <Loading />;
    return (
        <Box className="bookDateCover">
            <Box>
                <Swiper
                    style={{
                        paddingLeft: '23px',
                        paddingRight: '23px',
                    }}
                    initialSlide={dateList.findIndex((date) => date.date === dateFilter[0])}
                    className="dateSwiper"
                    spaceBetween={15}
                    slidesPerView={4}
                    slideToClickedSlide
                    navigation
                    modules={[Navigation]}
                >
                    {dateList &&
                        dateList.map((date) => (
                            <SwiperSlide className="dateSlide" key={date.date}>
                                <Button onClick={() => onClickDate(date.date)} className={retWeekClass(date.date)}>
                                    <Typography>{date.date.slice(0, 4)}</Typography>
                                    <Typography>{date.date.slice(5, 10)}</Typography>
                                    <Typography>{retWeek(date.date)}</Typography>
                                </Button>
                            </SwiperSlide>
                        ))}
                </Swiper>
            </Box>
            <Box className="filterCover">
                {dateFilter[0] && (
                    <Typography className={retWeekFilterClass(dateFilter[0])}>
                        {dateFilter[0]} {retWeek(dateFilter[0])}
                    </Typography>
                )}
            </Box>
        </Box>
    );
};

export default BookDateList;

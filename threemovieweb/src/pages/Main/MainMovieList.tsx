import React, {SyntheticEvent, useState} from 'react';
import {Star} from '@mui/icons-material';
import {Box, Button, Typography} from '@mui/material';
import {useRecoilValue, useSetRecoilState} from 'recoil';
import SwiperCore, {Navigation} from 'swiper';
import {Swiper, SwiperSlide} from 'swiper/react';

import 'swiper/scss';
import 'swiper/scss/navigation';
import moviePlaceHolder from '../../assets/images/MoviePlaceHolder.jpg';
import movieListAtom from '../../Recoil/Atom/movieLIstAtom';
import movieNowAtom from '../../Recoil/Atom/movieNowAtom';

const MainMovieList = () => {
    const [hover, setHover] = useState(-1);
    const setMovieNow = useSetRecoilState(movieNowAtom);
    const movieList = useRecoilValue(movieListAtom);

    const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
        e.currentTarget.src = `${moviePlaceHolder}`;
    };

    const handleMovieNow = (index: number) => {
        const {movieId} = movieList[index];
        setMovieNow(movieId);
    };

    const showHoverButton = (i: number) => {
        setHover(i);
    };

    const hideHoverButton = () => {
        setHover(-1);
    };

    const onClickDetail = (movieId: string) => {
        document.location.href = `/moviedetail?movie=${movieId}`;
    };

    const onClickBookInfo = (movieId: string) => {
        document.location.href = `/bookinfo?movie=${movieId}`;
    };

    SwiperCore.use([Navigation]);
    return (
        <Swiper
            onSlideChange={(swiper) => handleMovieNow(swiper.realIndex)}
            style={{paddingLeft: '40px', paddingRight: '40px'}}
            className="listSwiper"
            spaceBetween={40}
            slidesPerView={4}
            breakpoints={{
                1024: {
                    slidesPerView: 4,
                },
                1460: {
                    slidesPerView: 5,
                },
                1980: {
                    slidesPerView: 6,
                },
            }}
            slideToClickedSlide
            loop
            navigation
            modules={[Navigation]}
            centeredSlides
        >
            {movieList.map((movieinfo, index) => (
                <SwiperSlide
                    key={movieinfo.movieId}
                    onMouseLeave={hideHoverButton}
                    onMouseEnter={() => showHoverButton(index)}
                >
                    <Box
                        className="posterinfo"
                        sx={{
                            fontFamily: 'NanumSquareRound',
                            filter: hover === index ? 'brightness(50%)' : 'brightness(100%)',
                        }}
                    >
                        <img src={movieinfo.poster || ''} onError={onErrorImg} alt=""/>
                        <Typography className="typo">{index + 1}</Typography>
                    </Box>

                    {hover === index && (
                        <Box className="buttonGroup">
                            <Button onClick={() => onClickDetail(movieinfo.movieId)}>상세 정보</Button>
                            <Button onClick={() => onClickBookInfo(movieinfo.movieId)}>시간표 정보</Button>
                        </Box>
                    )}

                    <Box className="movieInfo" sx={{fontFamily: 'NanumSquareRound'}}>
                        <Typography>{movieinfo.nameKR}</Typography>
                        <Box className="rankInfo">
                            <Typography>예매율 : {movieinfo.reservationRate}% | </Typography>
                            <Star sx={{marginLeft: '5px', marginRight: '5px', color: '#aacafd'}}/>
                            <Typography> {movieinfo.netizenAvgRate}</Typography>
                        </Box>
                    </Box>
                </SwiperSlide>
            ))}
        </Swiper>
    );
};

export default MainMovieList;

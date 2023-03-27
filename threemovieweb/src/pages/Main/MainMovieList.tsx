import React, { SyntheticEvent, useState } from 'react';
import { Box, Typography } from '@mui/material';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import SwiperCore, { Navigation } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/scss';
import 'swiper/scss/navigation';
import moviePlaceHolder from '../../assets/images/MoviePlaceHolder.jpg';
import movieListAtom from '../../Recoil/Atom/movieLIstAtom';
import movieNowAtom from '../../Recoil/Atom/movieNowAtom';

const MainMovieList = () => {
    const [swiper, setSwiper] = useState<SwiperCore>();
    const setMovieNow = useSetRecoilState(movieNowAtom);
    const movieList = useRecoilValue(movieListAtom);

    const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
        e.currentTarget.src = `${moviePlaceHolder}`;
    };

    const handleMovieNow = (index: number) => {
        const { movieId } = movieList[index];
        setMovieNow(movieId);
    };

    SwiperCore.use([Navigation]);
    return (
        <Swiper
            onSwiper={setSwiper}
            onSlideChange={(swiper) => handleMovieNow(swiper.activeIndex)}
            style={{ paddingLeft: '40px', paddingRight: '40px' }}
            className="listSwiper"
            spaceBetween={40}
            slidesPerView={4}
            breakpoints={{
                1024: {
                    slidesPerView: 4,
                },
                1600: {
                    slidesPerView: 5,
                },
                1920: {
                    slidesPerView: 6,
                },
            }}
            loop
            navigation
            modules={[Navigation]}
        >
            {movieList.map((movieinfo, index) => (
                <SwiperSlide key={movieinfo.movieId}>
                    <Box className="posterinfo" sx={{ fontFamily: 'NanumSquareRound' }}>
                        <img src={movieinfo.poster || ''} onError={onErrorImg} alt="" />
                        <Typography className="Typo">{index + 1}</Typography>
                    </Box>
                    <Box className="movieinfo" sx={{ fontFamily: 'NanumSquareRound' }}>
                        <Typography>{movieinfo.nameKR}</Typography>
                        <Typography>
                            예매율 {movieinfo.reservationRate} | {movieinfo.netizenAvgRate}
                        </Typography>
                    </Box>
                </SwiperSlide>
            ))}
        </Swiper>
    );
};

export default MainMovieList;

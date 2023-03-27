import React, { SyntheticEvent, useEffect } from 'react';
import { useRecoilValue } from 'recoil';
import SwiperCore, { Navigation, Pagination } from 'swiper';
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/scss';
import 'swiper/scss/navigation';
import 'swiper/scss/pagination';

import moviePlaceHolder from '../../assets/images/MoviePlaceHolder.jpg';
import movieListAtom from '../../Recoil/Atom/movieLIstAtom';
import movieInfoSelector from '../../Recoil/Selector/movieInfoSelector';

const MainPreview = () => {
    const movieList = useRecoilValue(movieListAtom);
    const movieinfo = useRecoilValue(movieInfoSelector);
    SwiperCore.use([Navigation, Pagination]);

    const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
        e.currentTarget.src = `${moviePlaceHolder}`;
    };

    useEffect(() => {
        console.log(movieinfo);
    }, [movieList]);

    return (
        <Swiper
            className="previewSwiper"
            slidesPerView={1}
            loop
            pagination={{ clickable: true }}
            navigation
            modules={[Pagination, Navigation]}
            autoplay={{ delay: 5000, disableOnInteraction: false }}
        >
            <SwiperSlide>slide 1</SwiperSlide>
            {movieinfo && !movieinfo.steelcuts && !movieinfo.trailer && (
                <SwiperSlide>
                    <img src={movieinfo.poster || ''} onError={onErrorImg} alt="" />
                </SwiperSlide>
            )}
            {movieinfo &&
                movieinfo.steelcuts &&
                movieinfo.steelcuts.length > 0 &&
                movieinfo.steelcuts?.map((steelcut) => (
                    <SwiperSlide>
                        <img src={steelcut} onError={onErrorImg} alt="" />
                    </SwiperSlide>
                ))}
        </Swiper>
    );
};

export default MainPreview;

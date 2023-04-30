import React, { useMemo, useState } from 'react';
import { Box } from '@mui/material';
import SwiperCore, { FreeMode, Navigation, Thumbs } from 'swiper';

import 'swiper/scss';
import 'swiper/scss/free-mode';
import 'swiper/scss/navigation';
import 'swiper/scss/thumbs';
import { Swiper, SwiperSlide } from 'swiper/react';
import { onErrorImg } from '../../Util/onErrorImg';

interface previewProps {
    steelcutsProp: string | undefined;
    trailerProp: string | undefined;
    nameKR: string | undefined;
}

const MoviePreview = ({ steelcutsProp, trailerProp, nameKR }: previewProps) => {
    const [thumbsSwiper, setThumbsSwiper] = useState<SwiperCore>();

    const stringToArray = (str: string | undefined) => {
        if (!str) return undefined;
        const ret = str.replace('[', '').replace(']', '').split(',');
        return ret;
    };

    const steelcuts = useMemo<string[] | undefined>(() => stringToArray(steelcutsProp), [steelcutsProp]);
    const trailer = useMemo<string[] | undefined>(() => stringToArray(trailerProp), [trailerProp]);

    SwiperCore.use([FreeMode, Navigation, Thumbs]);
    return (
        <Box className="previewCover">
            {(steelcutsProp || trailerProp) && (
                <Box>
                    <Swiper
                        className="previewSwiper"
                        loop
                        spaceBetween={10}
                        navigation
                        thumbs={{ swiper: thumbsSwiper && !thumbsSwiper.destroyed ? thumbsSwiper : null }}
                        modules={[FreeMode, Navigation, Thumbs]}
                    >
                        {Array.isArray(steelcuts) &&
                            steelcuts.map((steelcut) => (
                                <SwiperSlide key={steelcut}>
                                    <img src={steelcut || ''} onError={onErrorImg} alt="" />
                                </SwiperSlide>
                            ))}
                        {Array.isArray(trailer) &&
                            trailer.map((teaser) => (
                                <SwiperSlide key={teaser}>
                                    <iframe
                                        title={nameKR || teaser}
                                        width="100%"
                                        height="100%"
                                        src={`${teaser}?service=player_share`}
                                        allowFullScreen
                                        frameBorder="0"
                                        scrolling="no"
                                        allow="autopley; fullscreen; encrypted-media"
                                    />
                                </SwiperSlide>
                            ))}
                    </Swiper>
                    <Swiper
                        className="thumbSwiper"
                        onSwiper={setThumbsSwiper}
                        loop
                        spaceBetween={10}
                        slidesPerView={4}
                        freeMode
                        watchSlidesProgress
                        modules={[FreeMode, Navigation, Thumbs]}
                    >
                        {Array.isArray(steelcuts) &&
                            steelcuts.map((steelcut) => (
                                <SwiperSlide key={steelcut}>
                                    <img src={steelcut || ''} onError={onErrorImg} alt="" />
                                </SwiperSlide>
                            ))}
                        {Array.isArray(trailer) &&
                            trailer.map((teaser) => (
                                <SwiperSlide key={teaser}>
                                    <iframe
                                        title={nameKR || teaser}
                                        src={`${teaser}?service=player_share`}
                                        allowFullScreen
                                        frameBorder="0"
                                        scrolling="no"
                                        allow="fullscreen; encrypted-media"
                                    />
                                </SwiperSlide>
                            ))}
                    </Swiper>
                </Box>
            )}
        </Box>
    );
};

export default MoviePreview;

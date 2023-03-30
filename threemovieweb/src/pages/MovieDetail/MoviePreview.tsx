import React, {SyntheticEvent, useEffect, useState} from 'react';
import {Box} from '@mui/material';
import SwiperCore, {FreeMode, Navigation, Thumbs} from "swiper";

import "swiper/css";
import "swiper/css/free-mode";
import "swiper/css/navigation";
import "swiper/css/thumbs";
import {Swiper, SwiperSlide} from "swiper/react";
import moviePlaceHolder from "../../assets/images/MoviePlaceHolder.jpg";

interface previewProps {
	steelcutsProp: string | undefined;
	trailerProp: string | undefined;
	nameKR: string | undefined;
}

const MoviePreview = ({steelcutsProp, trailerProp, nameKR}: previewProps) => {
	const [steelcuts, setSteelcuts] = useState<string[]>();
	const [trailer, setTrailer] = useState<string[]>();
	const [thumbsSwiper, setThumbsSwiper] = useState<SwiperCore>();
	
	const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
		e.currentTarget.src = `${moviePlaceHolder}`;
	};
	
	const stringToArray = (str: string | null) => {
		if (!str) return undefined;
		const ret = str.replace('[', '').replace(']', '').split(',');
		return ret;
	};
	
	useEffect(() => {
		console.log(steelcutsProp);
		if (steelcutsProp) {
			const steelcuts = stringToArray(steelcutsProp);
			setSteelcuts(steelcuts);
		}
		if (trailerProp) {
			const trailer = stringToArray(trailerProp);
			setTrailer(trailer);
		}
	}, [nameKR]);
	
	SwiperCore.use([FreeMode, Navigation, Thumbs]);
	return (
		<Box className="previewCover">
			<Swiper loop
			        spaceBetween={10}
			        navigation
			        thumbs={{swiper: thumbsSwiper}}
			        modules={[FreeMode, Navigation, Thumbs]}>
				{steelcuts && steelcuts.map((steelcut) => (
					<SwiperSlide key={steelcut}>
						<img src={steelcut || ''} onError={onErrorImg} alt=""/>
					</SwiperSlide>
				))}
				{trailer && trailer.map((teaser) => (
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
				onSwiper={setThumbsSwiper}
				loop
				spaceBetween={10}
				slidesPerView={4}
				freeMode
				watchSlidesProgress
				modules={[FreeMode, Navigation, Thumbs]}
				className="mySwiper"
			>
				{steelcuts && steelcuts.map((steelcut) => (
					<SwiperSlide key={steelcut}>
						<img src={steelcut || ''} onError={onErrorImg} alt=""/>
					</SwiperSlide>
				))}
				{trailer && trailer.map((teaser) => (
					<SwiperSlide>
						<SwiperSlide key={teaser}>
							<iframe
								title={nameKR}
								width="100%"
								height="100%"
								src={`${teaser}?service=player_share`}
								allowFullScreen
								frameBorder="0"
								scrolling="no"
								allow="autopley; fullscreen; encrypted-media"
							/>
						</SwiperSlide>
					</SwiperSlide>
				))}
			</Swiper>
		</Box>
	);
};

export default MoviePreview;

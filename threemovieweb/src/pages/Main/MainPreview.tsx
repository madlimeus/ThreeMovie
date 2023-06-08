import React, {useEffect, useState} from 'react';
import {useRecoilValue} from 'recoil';
import SwiperCore, {Navigation, Pagination} from 'swiper';
import {Swiper, SwiperSlide} from 'swiper/react';

import 'swiper/scss';
import 'swiper/scss/navigation';
import 'swiper/scss/pagination';
import movieInfoSelector from '../../Recoil/Selector/movieInfoSelector';
import {onErrorImg} from '../../Util/onErrorImg';
import MoviePreview from "../../interfaces/MoviePreview";

const MainPreview = () => {
	const [steelcuts, setSteelcuts] = useState<MoviePreview[]>();
	const [trailer, setTrailer] = useState<MoviePreview[]>();
	const movieData = useRecoilValue(movieInfoSelector);
	SwiperCore.use([Navigation, Pagination]);
	
	useEffect(() => {
		if (movieData) {
			const steelcuts = movieData.previews.filter((preview) =>
				preview.type === "image"
			);
			setSteelcuts(steelcuts);
			const trailer = movieData.previews.filter((preview) =>
				preview.type === "video"
			);
			setTrailer(trailer);
		}
	}, [movieData]);
	
	return (
		<Swiper
			className="previewSwiper"
			slidesPerView={1}
			loop
			pagination={{clickable: true}}
			navigation
			modules={[Pagination, Navigation]}
			autoplay={{delay: 3000, disableOnInteraction: false}}
		>
			{movieData && !steelcuts && !trailer && (
				<SwiperSlide>
					<img src={movieData.poster || ''} onError={onErrorImg} alt=""/>
				</SwiperSlide>
			)}
			{steelcuts &&
				steelcuts.map((steelcut) => (
					<SwiperSlide key={steelcut.link}>
						<img src={steelcut.link} onError={onErrorImg} alt=""/>
					</SwiperSlide>
				))}
			{trailer &&
				trailer.map((teaser) => (
					<SwiperSlide key={teaser.link}>
						<iframe
							title={movieData.nameKR}
							width="100%"
							height="100%"
							src={`${teaser.link}?service=player_share`}
							allowFullScreen
							allow="autoplay; fullscreen; encrypted-media"
						/>
					</SwiperSlide>
				))}
		</Swiper>
	);
};

export default MainPreview;

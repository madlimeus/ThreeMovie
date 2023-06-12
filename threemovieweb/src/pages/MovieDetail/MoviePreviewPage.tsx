import React, {useState} from 'react';
import {Box} from '@mui/material';
import SwiperCore, {FreeMode, Navigation, Thumbs} from 'swiper';

import 'swiper/scss';
import 'swiper/scss/free-mode';
import 'swiper/scss/navigation';
import 'swiper/scss/thumbs';
import {Swiper, SwiperSlide} from 'swiper/react';
import {onErrorImg} from '../../Util/onErrorImg';
import {MoviePreview} from "../../interfaces/MovieData";

interface previewProps {
	previews: [MoviePreview];
	nameKr: string | undefined;
}

const MoviePreviewPage = ({previews, nameKr}: previewProps) => {
	const steelcuts = previews.filter((preview) =>
		preview.type === "image"
	);
	
	const trailer = previews.filter((preview) =>
		preview.type === "video"
	);
	
	const [thumbsSwiper, setThumbsSwiper] = useState<SwiperCore>();
	
	SwiperCore.use([FreeMode, Navigation, Thumbs]);
	return (
		<Box className="previewCover">
			{previews.length > 0 && (
				<Box>
					<Swiper
						className="previewSwiper"
						loop
						spaceBetween={10}
						navigation
						thumbs={{swiper: thumbsSwiper && !thumbsSwiper.destroyed ? thumbsSwiper : null}}
						modules={[FreeMode, Navigation, Thumbs]}
					>
						{Array.isArray(steelcuts) &&
							steelcuts.map((steelcut) => (
								<SwiperSlide key={steelcut.link}>
									<img src={steelcut.link || ''} onError={onErrorImg} alt=""/>
								</SwiperSlide>
							))}
						{Array.isArray(trailer) &&
							trailer.map((teaser) => (
								<SwiperSlide key={teaser.link}>
									<iframe
										title={teaser.link}
										width="100%"
										height="100%"
										src={`${teaser.link}?service=player_share`}
										allowFullScreen
										allow="autoplay; fullscreen; encrypted-media"
									/>
								</SwiperSlide>
							))}
					</Swiper>
					<Swiper
						className="thumbSwiper"
						onSwiper={setThumbsSwiper}
						loop
						spaceBetween={10}
						slidesPerView="auto"
						freeMode
						watchSlidesProgress
						modules={[FreeMode, Navigation, Thumbs]}
					>
						{steelcuts.length > 0 &&
							steelcuts.map((steelcut) => (
								<SwiperSlide key={steelcut.link}>
									<img src={steelcut.link || ''} onError={onErrorImg} alt=""/>
								</SwiperSlide>
							))}
						{trailer.length > 0 &&
							trailer.map((teaser) => (
								<SwiperSlide key={teaser.link}>
									<iframe
										title={nameKr || teaser.link}
										src={`${teaser.link}?service=player_share`}
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

export default MoviePreviewPage;

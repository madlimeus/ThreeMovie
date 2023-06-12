import React from 'react';
import {Star} from '@mui/icons-material';
import {Box, Button, Typography} from '@mui/material';
import {locateBookData} from '../../Util/locateUtil';
import {onErrorImg} from '../../Util/onErrorImg';
import {MovieHeader} from "../../interfaces/MovieData";
import dateFormat from "../../Util/dateFormat";

const MovieHeaderPage = ({movieHeader}: { movieHeader: MovieHeader }) => {
	const {
		movieId = movieHeader.movieId,
		nameKr = movieHeader.nameKr,
		nameEn = movieHeader.nameEn,
		netizenAvgRate = movieHeader.netizenAvgRate,
		releaseDate = movieHeader.releaseDate,
		reservationRate = movieHeader.reservationRate,
		category = movieHeader.category,
		poster = movieHeader.poster,
		runningTime = movieHeader.runningTime,
		admissionCode = movieHeader.admissionCode,
		country = movieHeader.country,
		reservationRank = movieHeader.reservationRank,
		totalAudience = movieHeader.totalAudience
	} = movieHeader
	
	const removeArr = () => {
		if (category) return category.replace('[', '').replace(']', '').replaceAll(',', ', ');
		return category;
	};
	
	return (
		<Box className="headerCover">
			<img className="poster" src={poster || ''} onError={onErrorImg} alt=""/>
			<Box>
				<Typography className="title">{nameKr}</Typography>
				
				<Box className="contentCover">
					{releaseDate && (
						<Box className="contentBox">
							<Typography className="contentType">개봉</Typography>
							<Typography className="content">{dateFormat(releaseDate)}</Typography>
						</Box>
					)}
					{category && (
						<Box className="contentBox">
							<Typography className="contentType">장르</Typography>
							<Typography className="content">{removeArr()}</Typography>
						</Box>
					)}
					{typeof reservationRate !== undefined && reservationRate !== 0 && (
						<Box className="contentBox">
							<Typography className="contentType">예매율</Typography>
							<Typography className="content">{reservationRate} %</Typography>
						</Box>
					)}
					{typeof netizenAvgRate !== undefined && netizenAvgRate !== 0 && (
						<Box className="contentBox">
							<Typography className="contentType">평점</Typography>
							<Star sx={{marginRight: '5px', color: '#aacafd'}}/>
							<Typography className="content">{netizenAvgRate}</Typography>
						</Box>
					)}
					{runningTime && (
						<Box className="contentBox">
							<Typography className="contentType">상영시간</Typography>
							<Typography className="content">{runningTime}</Typography>
						</Box>
					)}
					{admissionCode && (
						<Box className="contentBox">
							<Typography className="contentType">연령 등급</Typography>
							<Typography className="content">{admissionCode}</Typography>
						</Box>
					)}
					{country && (
						<Box className="contentBox">
							<Typography className="contentType">제작 국가</Typography>
							<Typography className="content">{country.replace(']', '').replace('[', '')}</Typography>
						</Box>
					)}
					{reservationRank && (
						<Box className="contentBox">
							<Typography className="contentType">박스오피스</Typography>
							<Typography className="content">{reservationRank} 등</Typography>
						</Box>
					)}
					{totalAudience && (
						<Box className="contentBox">
							<Typography className="contentType">관객수</Typography>
							<Typography
								className="content">{totalAudience.replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",")} 명</Typography>
						</Box>
					)}
				</Box>
				<Button onClick={() => locateBookData(movieId)} className="bookButton">
					예약하기
				</Button>
			</Box>
		</Box>
	);
};

export default MovieHeaderPage;

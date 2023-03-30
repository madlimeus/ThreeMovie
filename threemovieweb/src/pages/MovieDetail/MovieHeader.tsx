import React, {SyntheticEvent} from 'react';
import {Star} from '@mui/icons-material';
import {Box, Button, Typography} from '@mui/material';
import moviePlaceHolder from '../../assets/images/MoviePlaceHolder.jpg';

interface headerProps {
	movieId: string | undefined;
	nameKR: string | undefined;
	nameEN: string | undefined;
	netizenAvgRate: number | undefined;
	reservationRate: number | undefined;
	releaseDate: string | undefined;
	poster: string | undefined;
	category: string | undefined;
	runningTime: number | undefined;
	admissionCode: string | undefined;
	country: string | undefined;
	reservationRank: string | undefined;
	totalAudience: string | undefined;
}

const MovieHeader = ({
	                     movieId,
	                     nameKR,
	                     nameEN,
	                     netizenAvgRate,
	                     reservationRate,
	                     releaseDate,
	                     poster,
	                     category,
	                     runningTime,
	                     admissionCode,
	                     country,
	                     reservationRank,
	                     totalAudience,
                     }: headerProps) => {
	const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
		e.currentTarget.src = `${moviePlaceHolder}`;
	};
	
	const onClickBookInfo = () => {
		document.location.href = `/bookinfo?movie=${movieId}`;
	};
	
	const retDate = () => {
		if (releaseDate) return `${releaseDate.slice(0, 4)}.${releaseDate.slice(4, 6)}.${releaseDate.slice(6, 8)}`;
		return releaseDate;
	};
	
	const removeArr = () => {
		if (category) return category.replace('[', '').replace(']', '').replaceAll(',', ', ');
		return category;
	};
	
	return (
		<Box className="headerCover">
			<img className="poster" src={poster || ''} onError={onErrorImg} alt=""/>
			<Box>
				<Typography className="title">{nameKR}</Typography>
				
				<Box className="contentCover">
					{releaseDate && (
						<Box className="contentBox">
							<Typography className="contentType">개봉</Typography>
							<Typography className="content">{retDate()}</Typography>
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
							<Typography className="content">{admissionCode}</Typography>
						</Box>
					)}
					{country && (
						<Box className="contentBox">
							<Typography className="contentType">제작 국가</Typography>
							<Typography className="content">{country}</Typography>
						</Box>
					)}
					{reservationRank && (
						<Box className="contentBox">
							<Typography className="contentType">박스오피스</Typography>
							<Typography className="content">{reservationRank}</Typography>
						</Box>
					)}
					{totalAudience && (
						<Box className="contentBox">
							<Typography className="contentType">관객수</Typography>
							<Typography className="content">{totalAudience}</Typography>
						</Box>
					)}
				</Box>
				<Button onClick={onClickBookInfo} className="bookButton">
					예약하기
				</Button>
			</Box>
		</Box>
	);
};

export default MovieHeader;

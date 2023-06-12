import React from 'react';
import {Box, Typography} from '@mui/material';
import {Recommend} from "@mui/icons-material";
import {MovieReview} from "../../interfaces/MovieData";
import onTheaterImage from "../../Util/onTheaterImage";
import VerticalDivider from "../../layout/VerticalDivider";
import dateFormat from "../../Util/dateFormat";

const MovieReviewBox = ({review, date, recommendation, movieTheater}: MovieReview) => {
	return (
		<Box className="reviewCover">
			<Box className="movieTheater">
				<img
					className="theaterImg"
					src={onTheaterImage(movieTheater)}
					alt={movieTheater}
				/>
			</Box>
			<VerticalDivider/>
			
			<Box className="review">
				<Typography>{review}</Typography>
			</Box>
			<VerticalDivider/>
			
			<Box className="recDateBox">
				<Box className="recommendation">
					<Typography>{recommendation}</Typography>
					<Recommend/>
				</Box>
				<Box className="date">
					<Typography>{dateFormat(date)}</Typography>
				</Box>
			</Box>
		</Box>
	);
};

export default MovieReviewBox;

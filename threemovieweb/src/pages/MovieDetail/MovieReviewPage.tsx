import React from 'react';
import {Box} from '@mui/material';
import {MovieReview} from "../../interfaces/MovieData";
import MovieReviewBox from "./MovieReviewBox";

const MovieReviewPage = ({reviews}: { reviews: [MovieReview] }) => {
	return (
		<Box>
			{
				reviews.map((review) => (
					<MovieReviewBox review={review.review} date={review.date} recommendation={review.recommendation}
					                movieTheater={review.movieTheater} key={review.review}/>
				))}
		</Box>
	);
}

export default MovieReviewPage;

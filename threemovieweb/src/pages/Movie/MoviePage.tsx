import React, {useEffect} from 'react';
import {Box} from '@mui/material';
import {useLocation} from "react-router-dom";
import QueryString from "qs";
import useAxios from '../../hook/useAxios';
import MovieProfile from '../../layout/MovieProfile';
import '../../style/scss/_moviePage.scss';
import {MovieSearchData} from "../../interfaces/MovieData";


const MainMovieList = () => {
	const location = useLocation();
	const queryData = QueryString.parse(location.search, {ignoreQueryPrefix: true});
	const [{response}, refetch] = useAxios<MovieSearchData[]>({
		method: 'get',
		url: `/movie/search/${queryData.keyword ?? ''}`,
		config: {
			headers: {'Content-Type': `application/json`},
		},
	});
	
	useEffect(() => {
		refetch();
	}, []);
	
	return (
		<Box className="moviePageBox">
			{response &&
				response.map((movieData, index) => (
					<Box className="moviePageProfileBox" key={movieData.movieId}>
						<MovieProfile
							movieId={movieData.movieId}
							poster={movieData.poster}
							nameKr={movieData.nameKr}
							reservationRate={movieData.reservationRate}
							netizenAvgRate={movieData.netizenAvgRate}
							index={index}
						/>
					</Box>
				))}
		</Box>
	);
};

export default MainMovieList;

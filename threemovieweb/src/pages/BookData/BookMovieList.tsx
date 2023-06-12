import React, {useEffect, useState} from 'react';
import {Box, Button, Input, InputAdornment, Typography} from '@mui/material';
import {useRecoilState} from 'recoil';
import SearchIcon from '@mui/icons-material/Search';
import {GET_MOVIE_LIST} from '../../gql/showtime.gql';
import bookMovieFilterAtom from '../../Recoil/Atom/bookMovieFilterAtom';
import {onErrorImg} from '../../Util/onErrorImg';
import useGraphQL from "../../hook/useGraphQL";
import {BookMovie} from "../../interfaces/BookShowTime";

const BookMovieList = () => {
	const [search, setSearch] = useState<string>('');
	const [movieList, setMovieList] = useState<BookMovie[]>([]);
	const [movieFilter, setMovieFilter] = useRecoilState<string[]>(bookMovieFilterAtom);
	const {data, refetch} = useGraphQL({query: GET_MOVIE_LIST, filter: false});
	
	const onSearch = (movie: BookMovie) => {
		if (movie.movieKr.toUpperCase().search(search.toUpperCase()) !== -1) return true;
		if (movie.movieEn && movie.movieEn.toUpperCase().search(search.toUpperCase()) !== -1) return true;
		if (movie.country && movie.country.search(search) !== -1) return true;
		if (movie.category && movie.category.search(search) !== -1) return true;
		return false;
	};
	
	const onChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
		setSearch(event.target.value);
	};
	
	const onClick = (movieId: string) => {
		if (movieFilter.includes(movieId)) setMovieFilter(movieFilter.filter((item) => item !== movieId));
		else {
			let arr = Array.from(new Set(movieFilter.concat(movieId)));
			
			if (arr.length > 3) arr = arr.slice(1, 4);
			setMovieFilter(arr);
		}
	};
	
	useEffect(() => {
		refetch()
	}, []);
	
	useEffect(() => {
		if (data) {
			setMovieList(data.getMovieList);
		}
	}, [data]);
	
	return (
		<Box className="bookMovieCover">
			<Box>
				<Input
					disableUnderline
					className="searchField"
					placeholder="영화 정보(장르, 제목)를 입력 해주세요."
					endAdornment={
						<InputAdornment position="end">
							<SearchIcon/>
						</InputAdornment>
					}
					value={search}
					onChange={onChange}
					inputProps={{
						maxLength: 30,
					}}
				/>
			</Box>
			<Box className="movielistCover">
				{movieList.length > 0 &&
					(movieList.find((item) => onSearch(item)) ? (
						movieList
							.filter((item) => onSearch(item))
							.map((movie) => (
								<Button
									key={movie.movieId}
									className={
										movieFilter.includes(movie.movieId) ? 'active movieButton' : 'movieButton'
									}
									onClick={() => onClick(movie.movieId)}
								>
									{movie.movieKr}
								</Button>
							))
					) : (
						<Typography className="searchNone">검색 결과가 없습니다.</Typography>
					))}
			</Box>
			<Box className="movieFilterCover">
				{movieFilter.length > 0 &&
					movieFilter.map((filter) => (
						<Box onClick={() => onClick(filter)} className="filterCover" key={filter}>
							<img
								className="filterImg"
								src={movieList.find((state) => state.movieId === filter)?.poster ?? ''}
								alt=""
								onError={onErrorImg}
							/>
							<Typography className="filterMovie">
								{movieList.find((state) => state.movieId === filter)?.movieKr}
							</Typography>
						</Box>
					))}
			</Box>
		</Box>
	);
};

export default BookMovieList;

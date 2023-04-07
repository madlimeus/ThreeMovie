import React, { SyntheticEvent, useEffect, useState } from 'react';
import { Box, Button, Input, InputAdornment, Typography } from '@mui/material';
import { useQuery } from '@apollo/react-hooks';
import { useRecoilState } from 'recoil';
import SearchIcon from '@mui/icons-material/Search';
import { GET_MOVIE_LIST } from '../../gql/showtime.gql';
import Loading from '../Loading';
import bookMovieFilterAtom from '../../Recoil/Atom/bookMovieFilterAtom';
import BookMovie from '../../interfaces/BookMovie';
import moviePlaceHolder from '../../assets/images/MoviePlaceHolder.jpg';

const BookMovieList = () => {
    const [search, setSearch] = useState<string>('');
    const [movieList, setMovieList] = useState<BookMovie[]>([]);
    const [movieFilter, setMovieFilter] = useRecoilState<string[]>(bookMovieFilterAtom);
    const { loading, error, data } = useQuery(GET_MOVIE_LIST);

    const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
        e.currentTarget.src = `${moviePlaceHolder}`;
    };

    const onSearch = (movie: BookMovie) => {
        if (movie.movieKR.toUpperCase().search(search.toUpperCase()) !== -1) return true;
        if (movie.movieEN && movie.movieEN.toUpperCase().search(search.toUpperCase()) !== -1) return true;
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
        if (data) {
            setMovieList(data.getMovieList);
        }
    }, [data]);

    if (loading) return <Loading />;
    return (
        <Box className="bookMovieCover">
            <Box>
                <Input
                    disableUnderline
                    className="searchField"
                    placeholder="영화 정보(장르, 제목)를 입력 해주세요."
                    endAdornment={
                        <InputAdornment position="end">
                            <SearchIcon />
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
                                    {movie.movieKR}
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
                                {movieList.find((state) => state.movieId === filter)?.movieKR}
                            </Typography>
                        </Box>
                    ))}
            </Box>
        </Box>
    );
};

export default BookMovieList;

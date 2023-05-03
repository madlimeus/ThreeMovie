import React, { useEffect } from 'react';
import { Box } from '@mui/material';

import useAxios from '../../hook/useAxios';
import MovieData from '../../interfaces/MovieData';
import MovieProfile from '../../layout/MovieProfile';
import '../../style/scss/_moviePage.scss';

const MainMovieList = () => {
    const [{ response, loading, error }, refetch] = useAxios<MovieData[]>({
        method: 'get',
        url: '/movieinfo/movielist',
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    useEffect(() => {
        refetch();
    }, []);

    return (
        <Box className="moviePageBox">
            {response &&
                response.map((movieData, index) => (
                    <Box className="moviePageProfileBox">
                        <MovieProfile
                            movieId={movieData.movieId}
                            poster={movieData.poster}
                            nameKR={movieData.nameKR}
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

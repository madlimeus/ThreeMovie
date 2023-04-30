import React, { useEffect } from 'react';
import { Box } from '@mui/material';

import useAxios from '../../hook/useAxios';
import Movieinfo from '../../interfaces/Movieinfo';
import MovieProfile from '../../layout/MovieProfile';
import '../../style/scss/_moviePage.scss';

const MainMovieList = () => {
    const [{ response, loading, error }, refetch] = useAxios<Movieinfo[]>({
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
                response.map((movieinfo, index) => (
                    <Box className="moviePageProfileBox">
                        <MovieProfile
                            movieId={movieinfo.movieId}
                            poster={movieinfo.poster}
                            nameKR={movieinfo.nameKR}
                            reservationRate={movieinfo.reservationRate}
                            netizenAvgRate={movieinfo.netizenAvgRate}
                            index={index}
                        />
                    </Box>
                ))}
        </Box>
    );
};

export default MainMovieList;

import React, { useEffect } from 'react';
import { TabContext, TabList } from '@mui/lab';
import { Box, Divider, Tab } from '@mui/material';
import QueryString from 'qs';
import { useLocation } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import useAxios from '../../assets/hook/useAxios';
import MovieDetail from '../../interfaces/MovieDetail';
import movieDetailTabAtom from '../../Recoil/Atom/movieDetailTabAtom';
import '../../style/scss/_moviedetail.scss';
import Loading from '../Loading';
import MovieHeader from './MovieHeader';

const MovieDetailPage = () => {
    const [value, setValue] = useRecoilState(movieDetailTabAtom);
    const location = useLocation();
    const queryData = QueryString.parse(location.search, { ignoreQueryPrefix: true });
    const { response, loading, error } = useAxios<MovieDetail>({
        method: 'get',
        url: `/api/movieinfo/moviedetail/${queryData.movie}`,
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    const handleChange = (event: React.SyntheticEvent, newValue: string) => {
        setValue(newValue);
    };

    useEffect(() => {
        console.log(response);
    }, [response]);

    if (loading) <Loading />;
    return (
        <Box className="flexbox detailCover">
            <MovieHeader
                movieId={response?.movieId}
                nameKR={response?.nameKR}
                nameEN={response?.nameEN}
                netizenAvgRate={response?.netizenAvgRate}
                releaseDate={response?.releaseDate}
                reservationRate={response?.reservationRate}
                category={response?.category}
                poster={response?.poster}
            />
            <Divider className="divide" variant="middle" />
            <TabContext value={value}>
                <TabList value={value} onChange={handleChange}>
                    <Tab label="detail" value="detail" />
                    <Tab label="creator" value="creator" />
                    <Tab label="preview" value="preview" />
                    <Tab label="review" value="review" />
                </TabList>
            </TabContext>
        </Box>
    );
};

export default MovieDetailPage;

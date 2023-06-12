import React, {useEffect} from 'react';
import {TabContext, TabList, TabPanel} from '@mui/lab';
import {Box, Divider, Tab} from '@mui/material';
import QueryString from 'qs';
import {useLocation} from 'react-router-dom';
import {useRecoilState} from 'recoil';
import useAxios from '../../hook/useAxios';
import movieDetailTabAtom from '../../Recoil/Atom/movieDetailTabAtom';
import '../../style/scss/_moviedetail.scss';
import {MovieDetail} from "../../interfaces/MovieData";
import MovieHeaderPage from './MovieHeaderPage';
import MovieMakingNote from "./MovieMakingNote";
import MovieCreatorPage from "./MovieCreatorPage";
import MoviePreviewPage from "./MoviePreviewPage";
import MovieReviewPage from "./MovieReviewPage";

const MovieDetailPage = () => {
	const [value, setValue] = useRecoilState(movieDetailTabAtom);
	const location = useLocation();
	const queryData = QueryString.parse(location.search, {ignoreQueryPrefix: true});
	const [{response}, refetch] = useAxios<MovieDetail>({
		method: 'get',
		url: `/movie/detail/${queryData.movie}`,
		config: {
			headers: {'Content-Type': `application/json`},
		},
	});
	
	const handleChange = (event: React.SyntheticEvent, newValue: string) => {
		setValue(newValue);
	};
	
	useEffect(() => {
		refetch();
	}, []);
	
	
	return (
		<Box>
			{response &&
                <Box className="flexbox detailCover">
                    <MovieHeaderPage
                        movieHeader={response}
                    />
                    <Divider className="divide"/>
                    <TabContext value={value}>
                        <Box className="tabCover">
                            <TabList value={value} onChange={handleChange}>
                                <Tab label="상세 정보" value="detail"/>
								{response.creators &&
                                    <Tab label="감독/배우" value="creator"/>}
								{response.previews &&
                                    <Tab label="미리보기" value="preview"/>}
								{response.reviews &&
                                    <Tab label="리뷰" value="review"/>}
                            </TabList>
                        </Box>
                        <TabPanel value="detail">
                            <MovieMakingNote makingNote={response.makingNote} summary={response.summary}/>
                        </TabPanel>
						
						{response.creators && <TabPanel value="creator">
                            <MovieCreatorPage creators={response.creators}/>
                        </TabPanel>}
						
						{response.previews && <TabPanel value="preview">
                            <MoviePreviewPage
                                previews={response.previews}
                                nameKr={response.nameKr}
                            />
                        </TabPanel>}
						
						{response.reviews && <TabPanel value="review">
                            <MovieReviewPage
                                reviews={response.reviews}
                            />
                        </TabPanel>}

                    </TabContext>
                </Box>
			}
		
		</Box>
	);
};
export default MovieDetailPage;

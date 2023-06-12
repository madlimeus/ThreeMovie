import React, {useEffect, useState} from 'react';
import {Box, Button, Divider, Typography} from '@mui/material';
import {useRecoilValue} from 'recoil';
import {GET_SHOW_TIME_LIST} from '../../gql/showtime.gql';
import bookBrchFilterAtom from '../../Recoil/Atom/bookBrchFilterAtom';
import bookDateFilterAtom from '../../Recoil/Atom/bookDateFilterAtom';
import {BookShowTime} from '../../interfaces/BookShowTime';
import useGraphQL from "../../hook/useGraphQL";
import {chgTimeForm} from "../../Util/dateUtil";

const TheaterShowTime = () => {
	const brchFilter = useRecoilValue(bookBrchFilterAtom);
	const dateFilter = useRecoilValue(bookDateFilterAtom);
	const [showTimeList, setShowTimeList] = useState<BookShowTime[]>([]);
	const {data, refetch} = useGraphQL({query: GET_SHOW_TIME_LIST});
	
	
	useEffect(() => {
		if (data) setShowTimeList(data.getShowTimeList);
	}, [data]);
	
	useEffect(() => {
		if (brchFilter.length > 0 && dateFilter.length > 0) {
			refetch();
		}
	}, [brchFilter, dateFilter]);
	
	return (
		<Box className="showTimeListCover">
			{showTimeList &&
				showTimeList.map((showTime) => (
					<Box key={`${showTime.movieKr}${showTime.brchKr}${showTime.screenKr}`} className="showTimeBox">
						<Box className="movieBrchCover">
							<Box className="movieBrchName">
								<Typography className="movieTitle">{showTime.movieKr}</Typography>
							</Box>
							
							<Box className="nameImg">
								<Typography>
									{showTime.playKind} | {showTime.screenKr} | {showTime.totalSeat}명
								</Typography>
							</Box>
						</Box>
						<Box className="showTimeCover">
							{showTime.res
								.map((item) => (
									<Box key={item.ticketPage}>
										<Button
											onClick={() =>
												window.open(`${item.ticketPage}`)
											}
											className="showTimeButton"
										>
											<Typography>{chgTimeForm(item.startTime)}</Typography>
											<Typography>{item.restSeat}명</Typography>
										</Button>
									</Box>
								))}
						</Box>
						<Divider className="divide"/>
					</Box>
				))}
		</Box>
	);
};

export default TheaterShowTime;

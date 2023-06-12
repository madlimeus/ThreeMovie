import React, {useEffect, useState} from 'react';
import {Box, Button, Divider, Typography} from '@mui/material';
import {useRecoilState, useRecoilValue, useSetRecoilState} from 'recoil';
import {GET_SHOW_TIME_LIST} from '../../gql/showtime.gql';
import bookMovieFilterAtom from '../../Recoil/Atom/bookMovieFilterAtom';
import bookBrchFilterAtom from '../../Recoil/Atom/bookBrchFilterAtom';
import bookDateFilterAtom from '../../Recoil/Atom/bookDateFilterAtom';
import {BookShowTime} from '../../interfaces/BookShowTime';
import bookAddrAtom from '../../Recoil/Atom/bookAddrAtom';
import bookTicketPageAtom from '../../Recoil/Atom/bookTicketPageAtom';
import onTheaterImage from "../../Util/onTheaterImage";
import useGraphQL from "../../hook/useGraphQL";
import {chgTimeForm} from "../../Util/dateUtil";

const BookShowTimeList = () => {
	const setSelectAddr = useSetRecoilState(bookAddrAtom);
	const [selectTicketPage, setSelectTicketPage] = useRecoilState(bookTicketPageAtom);
	const movieFilter = useRecoilValue(bookMovieFilterAtom);
	const brchFilter = useRecoilValue(bookBrchFilterAtom);
	const dateFilter = useRecoilValue(bookDateFilterAtom);
	const [showTimeList, setShowTimeList] = useState<BookShowTime[]>([]);
	const {data, refetch} = useGraphQL({query: GET_SHOW_TIME_LIST});
	
	
	const onClickShowTime = (movieTheater: string, brch: string, ticket: string) => {
		let addr = '';
		if (movieTheater === 'MB') addr = '메가박스';
		else if (movieTheater === 'LC') addr = '롯데시네마';
		else addr = 'CGV';
		addr += ` ${brch}`;
		setSelectAddr(addr);
		setSelectTicketPage(ticket);
	};
	
	useEffect(() => {
		if (data) setShowTimeList(data.getShowTimeList);
	}, [data]);
	
	useEffect(() => {
		if (movieFilter.length > 0 && brchFilter.length > 0 && dateFilter.length > 0) refetch();
		setSelectAddr('');
		setSelectTicketPage('');
	}, [movieFilter, brchFilter, dateFilter]);
	
	return (
		<Box className="showTimeListCover">
			{showTimeList &&
				showTimeList.map((showTime) => (
					<Box key={`${showTime.movieKr}${showTime.brchKr}${showTime.screenKr}`} className="showTimeBox">
						<Box className="movieBrchCover">
							<Box className="movieBrchName">
								<Typography className="movieTitle">{showTime.movieKr}</Typography>
								<Typography className="brchName">{showTime.brchKr}</Typography>
								<img
									className="theaterImg"
									src={onTheaterImage(showTime.movieTheater)}
									alt={showTime.movieTheater}
								/>
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
												onClickShowTime(showTime.movieTheater, showTime.brchKr, item.ticketPage)
											}
											className={
												item.ticketPage === selectTicketPage
													? 'active showTimeButton'
													: 'showTimeButton'
											}
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

export default BookShowTimeList;

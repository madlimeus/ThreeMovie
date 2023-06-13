import React, {useEffect, useState} from "react";
import {useRecoilState, useRecoilValue} from "recoil";
import SwiperCore, {Navigation} from "swiper";
import {Box, Button} from "@mui/material";
import {Swiper, SwiperSlide} from "swiper/react";
import bookBrchFilterAtom from "../../Recoil/Atom/bookBrchFilterAtom";
import bookDateFilterAtom from "../../Recoil/Atom/bookDateFilterAtom";
import useGraphQL from "../../hook/useGraphQL";
import {GET_DATE_LIST} from "../../gql/showtime.gql";
import DateTypo from "../../layout/DateTypo";
import {BookDate} from "../../interfaces/BookShowTime";

const TheaterDate = () => {
	const brchFilter = useRecoilValue(bookBrchFilterAtom);
	const [dateFilter, setDateFilter] = useRecoilState(bookDateFilterAtom);
	const [dateList, setDateList] = useState<BookDate[]>([]);
	const {data, refetch} = useGraphQL({query: GET_DATE_LIST});
	
	
	useEffect(() => {
		if (brchFilter.length > 0)
			refetch();
	}, [brchFilter]);
	
	useEffect(() => {
		if (data) {
			setDateList(data.getDateList);
		}
	}, [data]);
	
	SwiperCore.use([Navigation]);
	
	return (
		<Box className="theaterDateCover">
			<Box>
				<Swiper
					style={{
						paddingLeft: '23px',
						paddingRight: '23px',
					}}
					initialSlide={dateList.findIndex((date) => date.date === dateFilter[0])}
					className="dateSwiper"
					spaceBetween={40}
					slidesPerView="auto"
					slideToClickedSlide
					navigation
					modules={[Navigation]}
				>
					{dateList &&
						dateList.map((date) => (
							<SwiperSlide className="dateSlide"
							             key={date.date}>
								<Button onClick={() => setDateFilter([date.date])}
								        className={dateFilter[0] === date.date ? "dateButton active" : "dateButton"}>
									<DateTypo date={date.date}/>
								</Button>
							</SwiperSlide>
						))}
				</Swiper>
			</Box>
			<Box className="filterCover selectDate">
				{dateFilter[0] && (
					<DateTypo date={dateFilter[0]}/>
				)}
			</Box>
		</Box>
	);
};

export default TheaterDate;

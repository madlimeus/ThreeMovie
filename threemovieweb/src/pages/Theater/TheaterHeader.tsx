import React, {useEffect, useState} from 'react';
import {Box, Button, Tab} from "@mui/material";
import {TabContext, TabList, TabPanel} from "@mui/lab";
import {useRecoilState} from "recoil";
import BookBrchFilterAtom from "../../Recoil/Atom/bookBrchFilterAtom";
import BookMovieTheaterFilterAtom from "../../Recoil/Atom/bookMovieTheaterFilterAtom";
import useAxios from "../../hook/useAxios";
import "../../style/scss/_theaterHeader.scss";
import onTheaterImage from "../../Util/onTheaterImage";
import {Theater} from "../../interfaces/Theater";
import TextButton from "../../layout/TextButton";


const TheaterHeader = () => {
	const [value, setValue] = useState<string>("LC");
	const [brch, setBrch] = useRecoilState(BookBrchFilterAtom);
	const [theater, setTheater] = useRecoilState(BookMovieTheaterFilterAtom);
	const [city, setCity] = useState<string>("서울");
	
	const [{response}, refetch] = useAxios<Theater[]>({
		method: 'get',
		url: `/theater/main`,
		config: {
			headers: {'Content-Type': `application/json`},
		},
	});
	
	const handleTheaterChange = (event: React.SyntheticEvent, newValue: string) => {
		setValue(newValue);
		setTheater([newValue])
	};
	
	const handleBranchChange = (branch: string, movieTheater: string) => {
		setBrch([branch])
		setTheater([movieTheater])
	}
	
	const onClickCity = (city: string) => {
		setCity(city)
	}
	
	useEffect(() => {
		refetch();
	}, [])
	
	return (
		<TabContext value={value}>
			{response &&
                <Box className="theaterHeader">
                    <Box className="tabList">
                        <TabList className="tab" value={value} onChange={handleTheaterChange} orientation="vertical">
                            <Tab icon={<img src={onTheaterImage("LC")} alt="LotteCinema"/>} label="Lotte Cinema"
                                 value="LC"/>

                            <Tab icon={<img src={onTheaterImage("CGV")} alt="CGV"/>} label="CGV" value="CGV"/>

                            <Tab icon={<img src={onTheaterImage("MB")} alt="MegaBox"/>} label="Mega Box" value="MB"/>

                        </TabList>
                    </Box>

                    <Box className="tabPanel">
                        <TabPanel className={value === "LC" ? "activeTab cityTab" : "cityTab"} value="LC">
							{response.filter((theater) => theater.movieTheater === "LC").map((theater) => (
								<Button onClick={() => onClickCity(theater.city)} variant="text"
								        key={theater.city}
								        className={city === theater.city ? "activeButton cityButton" : "cityButton"}
								>{theater.city}</Button>
							))}
                        </TabPanel>
                        <TabPanel className={value === "CGV" ? "activeTab cityTab" : "cityTab"} value="CGV">
							{response.filter((theater) => theater.movieTheater === "CGV").map((theater) => (
								<Button onClick={() => onClickCity(theater.city)} variant="text"
								        key={theater.city}
								        className={city === theater.city ? "activeButton cityButton" : "cityButton"}>{theater.city}</Button>
							))}
                        </TabPanel>
                        <TabPanel className={value === "MB" ? "activeTab cityTab" : "cityTab"} value="MB">
							{response.filter((theater) => theater.movieTheater === "MB").map((theater) => (
								<Button onClick={() => onClickCity(theater.city)} variant="text"
								        key={theater.city}
								        className={city === theater.city ? "activeButton cityButton" : "cityButton"}>{theater.city}</Button>
							))}
                        </TabPanel>
                        <Box className="branchTab">
							{response.filter((theater) => theater.movieTheater === value && theater.city === city).map((theater) => (
								theater.branches.map((branch) => (
									<TextButton
										key={branch.brchKr}
										text={branch.brchKr}
										onClick={() => handleBranchChange(branch.brchKr, theater.movieTheater)}
										active={brch.includes(branch.brchKr)}/>
								))
							))}
                        </Box>
                    </Box>
                </Box>
			}
		
		</TabContext>
	
	);
}

export default TheaterHeader;

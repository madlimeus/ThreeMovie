import React from 'react';
import {Box} from '@mui/material';
import {locateBookData, locateMain, locateMovie, locateTheater} from '../../Util/locateUtil';
import VerticalDivider from "../../layout/VerticalDivider";
import TextButton from "../../layout/TextButton";

const NavMenu = () => {
	return (
		<Box className="navMenu">
			<TextButton text="메인" onClick={locateMain}/>
			<VerticalDivider/>
			
			<TextButton text="영화 목록" onClick={locateMovie}/>
			<VerticalDivider/>
			
			<TextButton text="영화관 목록" onClick={locateTheater}/>
			<VerticalDivider/>
			
			<TextButton text="시간표 목록" onClick={locateBookData}/>
		</Box>
	);
};

export default NavMenu;

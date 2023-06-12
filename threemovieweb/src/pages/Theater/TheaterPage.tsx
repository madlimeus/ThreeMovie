import React from 'react';
import {Box} from "@mui/material";
import TheaterBody from "./TheaterBody";
import TheaterHeader from "./TheaterHeader";
import "../../style/scss/_theaterMain.scss";

const TheaterPage = () => {
	return <Box className="flexbox theaterCover">
		<TheaterHeader/>
		<TheaterBody/>
	</Box>;
}

export default TheaterPage

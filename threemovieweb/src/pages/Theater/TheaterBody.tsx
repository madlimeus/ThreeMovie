import React from 'react';
import {Box} from "@mui/material";
import "../../style/scss/_theaterShowTime.scss";
import TheaterDate from "./TheaterDate";
import TheaterShowTime from "./TheaterShowTime";

const TheaterBody = () => {
	return <Box>
		<TheaterDate/>
		<TheaterShowTime/>
	</Box>;
}

export default TheaterBody;

import React from 'react';
import {Typography} from "@mui/material";
import "../style/scss/_dateTypo.scss";
import {chgDateListForm, retWeekClass} from "../Util/dateUtil";

interface dateProp {
	date: number;
}

const DateTypo = ({date}: dateProp) => {
	const dateStr = chgDateListForm(date);
	return <Typography className={retWeekClass(date)}>
		{dateStr}
	</Typography>
	
}

export default DateTypo;

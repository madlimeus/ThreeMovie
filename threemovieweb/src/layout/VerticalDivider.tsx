import React from "react";
import {Divider} from "@mui/material";
import "../style/scss/_verticalDivider.scss"

const VerticalDivider = () => {
	return (
		<Divider className="divide" variant="middle" orientation="vertical" flexItem sx={{
			marginLeft: "20px",
			marginRight: "20px",
		}}/>
	)
}

export default VerticalDivider;

import React from 'react';
import {Typography} from '@mui/material';
import "../style/scss/_textButton.scss";

interface textProp {
	text: string;
	onClick: () => void;
	active?: boolean;
}

const TextButton = ({text, onClick, active = false}: textProp) => {
	return <Typography className={active ? "active textButton" : "textButton"}
	                   onClick={() => onClick()}>{text}</Typography>;
};

export default TextButton;

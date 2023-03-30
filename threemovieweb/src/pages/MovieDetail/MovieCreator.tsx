import React, {SyntheticEvent, useEffect, useState} from 'react';
import {Box, Typography} from '@mui/material';
import moviePlaceHolder from "../../assets/images/MoviePlaceHolder.jpg";

interface creatorProps {
	Items: string | undefined;
}

interface creator {
	Role: string | undefined;
	NameKR: string | undefined;
	NameEN: string | undefined;
	PhotoAddress: string | undefined;
}

const MovieCreator = ({Items}: creatorProps) => {
	const [dat, setDat] = useState<creator | undefined>(undefined)
	
	const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
		e.currentTarget.src = `${moviePlaceHolder}`;
	};
	
	useEffect(() => {
		if (Items) {
			setDat(JSON.parse(Items));
		}
	}, [])
	
	return (
		<Box className="creatorCover">
			{Array.isArray(dat) && dat.map((item) => (
				<Box className="creatorBox" key={item.NameKR}>
					<img className="thumb" src={item.PhotoAddress || ''} onError={onErrorImg} alt=""/>
					<Box className="creator">
						<Typography className="role">{item.Role}</Typography>
						<Typography className="name">{item.NameKR}</Typography>
					</Box>
				</Box>
			))}
		</Box>
	);
};

export default MovieCreator;

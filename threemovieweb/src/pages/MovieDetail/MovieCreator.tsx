import React, {SyntheticEvent, useMemo} from 'react';
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
	const datArr = () => {
		if (Items)
			return JSON.parse(Items);
		return undefined;
	}
	
	const dat = useMemo<creator | undefined>(datArr, [Items]);
	
	const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
		e.currentTarget.src = `${moviePlaceHolder}`;
	};
	
	
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

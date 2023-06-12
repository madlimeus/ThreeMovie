import React from 'react';
import {Box, Typography} from '@mui/material';
import {onErrorImg} from '../../Util/onErrorImg';
import {MovieCreator} from "../../interfaces/MovieData";


const MovieCreatorPage = ({creators}: { creators: [MovieCreator] }) => {
	
	return (
		<Box className="creatorCover">
			{creators.length > 0 && (
				creators.map((creator) => (
					<Box className="creatorBox" key={creator.nameKr}>
						<img className="thumb" src={creator.link || ''} onError={onErrorImg} alt=""/>
						<Box className="creator">
							<Typography
								className="role">{creator.roleKr === "null" ? "연출자" : creator.roleKr}</Typography>
							<Typography className="name">{creator.nameKr}</Typography>
						</Box>
					</Box>
				)))}
		</Box>
	);
};

export default MovieCreatorPage;

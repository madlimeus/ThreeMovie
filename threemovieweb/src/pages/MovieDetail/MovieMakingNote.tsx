import React from 'react';
import {Box, Divider, Typography} from '@mui/material';

interface makingNoteProps {
	summary: string | undefined;
	makingNote: string | undefined;
}

const MovieMakingNote = ({summary, makingNote}: makingNoteProps) => {
	return (
		<Box className="makingNoteCover">
			{summary &&
                <Typography className="summary" dangerouslySetInnerHTML={{__html: summary}}/>
			}
			
			{makingNote &&
                <Box>
                    <Divider/>
                    <Typography dangerouslySetInnerHTML={{__html: makingNote}}/>
                </Box>
			}
		</Box>
	);
};

export default MovieMakingNote;

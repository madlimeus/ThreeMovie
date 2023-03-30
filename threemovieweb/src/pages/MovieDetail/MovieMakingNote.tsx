import React from 'react';
import { Box, Typography} from '@mui/material';

interface makingNoteProps {
    summary: string | undefined;
    makingNote: string | undefined;
}

const MovieMakingNote = ({summary, makingNote}: makingNoteProps) => {
    return (
        <Box className="makingNoteCover">
            {
                summary &&
                <Typography>
                    {summary}
                </Typography>
            }
            {
                makingNote &&
                <Box>
                    {makingNote}
                </Box>
            }
        </Box>
    );
};

export default MovieMakingNote;

import React from 'react';
import { Box} from '@mui/material';

interface makingNoteProps {
    summary: string | undefined;
    makingNote: string | undefined;
}

const MovieMakingNote = ({summary, makingNote}: makingNoteProps) => {
    return (
        <Box className="makingNoteCover">
            {
                summary &&
                <Box/>
            }
            {
                makingNote &&
                <Box/>
            }
        </Box>
    );
};

export default MovieMakingNote;

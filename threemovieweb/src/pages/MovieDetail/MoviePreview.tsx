import React from 'react';
import { Box } from '@mui/material';

interface previewProps {
    steelcuts : string | undefined;
    trailer : string | undefined;
}

const MoviePreview = ({steelcuts, trailer}:previewProps) => {
    return (
        <Box className="previewCover">
            <Box />
        </Box>
    );
};

export default MoviePreview;

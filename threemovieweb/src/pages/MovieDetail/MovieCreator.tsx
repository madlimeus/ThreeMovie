import React, { useEffect, useState } from 'react';
import { Box } from '@mui/material';

interface creatorProps {
    Items : string | undefined;
}

const MovieCreator = ({Items}: creatorProps) => {
    const [dat, setDat] = useState()
    
    useEffect(() => {
        if(Items)
            setDat(JSON.parse(Items))
    }, [Items])
    
    return (
        <Box className="creatorCover">
            <Box />
        </Box>
    );
};

export default MovieCreator;

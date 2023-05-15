import React, { useMemo } from 'react';
import { Box, Typography } from '@mui/material';
import { onErrorImg } from '../../Util/onErrorImg';

interface creatorProps {
    Items: string | undefined;
}

interface creator {
    Role: string | undefined;
    NameKR: string | undefined;
    NameEN: string | undefined;
    PhotoAddress: string | undefined;
}

const MovieCreator = ({ Items }: creatorProps) => {
    const datArr = () => {
        if (Items) return JSON.parse(Items);
        return undefined;
    };

    const dat = useMemo<creator | undefined>(datArr, [Items]);

    return (
        <Box className="creatorCover">
            {Array.isArray(dat) &&
                dat.map((item) => (
                    <Box className="creatorBox" key={item.NameKR}>
                        <img className="thumb" src={item.PhotoAddress || ''} onError={onErrorImg} alt="" />
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

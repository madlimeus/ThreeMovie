import React from 'react';
import { Box, Button, Typography } from '@mui/material';
import { Star } from '@mui/icons-material';
import { locateBookInfo, locateDetail } from '../Util/locateUtil';
import { onErrorImg } from '../Util/onErrorImg';
import '../style/scss/_movieProfile.scss';

interface creatorProps {
    movieId: string;
    poster: string | null;
    nameKR: string;
    reservationRate: number;
    netizenAvgRate: number;
    index: number;
}

const MovieProfile = ({ movieId, poster, nameKR, reservationRate, netizenAvgRate, index }: creatorProps) => {
    return (
        <Box className="movieProfileBox">
            <Box className="movieProfilePosterInfo">
                <img src={poster || ''} onError={onErrorImg} alt="" />
                <Typography className="movieProfileRank">{index + 1}</Typography>

                <Box className="movieProfileButtonGroup">
                    <Button onClick={() => locateDetail(movieId)}>상세 정보</Button>
                    <Button onClick={() => locateBookInfo(movieId)}>시간표 정보</Button>
                </Box>
            </Box>

            <Box className="movieProfileInfo">
                <Typography>{nameKR}</Typography>
                <Box className="movieProfileRankInfo">
                    <Typography>예매율 : {reservationRate}% | </Typography>
                    <Star sx={{ marginLeft: '5px', marginRight: '5px', color: '#aacafd' }} />
                    <Typography> {netizenAvgRate}</Typography>
                </Box>
            </Box>
        </Box>
    );
};

export default MovieProfile;

import React, { SyntheticEvent } from 'react';
import { Box, Button, Typography } from '@mui/material';
import moviePlaceHolder from '../../assets/images/MoviePlaceHolder.jpg';

interface headerProps {
    movieId: string | undefined;
    nameKR: string | undefined;
    nameEN: string | undefined;
    netizenAvgRate: number | undefined;
    reservationRate: number | undefined;
    releaseDate: string | undefined;
    poster: string | undefined;
    category: string | undefined;
}

const MovieHeader = ({
    movieId,
    nameKR,
    nameEN,
    netizenAvgRate,
    reservationRate,
    releaseDate,
    poster,
    category,
}: headerProps) => {
    const onErrorImg = (e: SyntheticEvent<HTMLImageElement, Event>) => {
        e.currentTarget.src = `${moviePlaceHolder}`;
    };

    const onClickBookInfo = () => {
        document.location.href = `/bookinfo?movie=${movieId}`;
    };

    const retDate = () => {
        if (releaseDate) return `${releaseDate.slice(0, 4)}.${releaseDate.slice(4, 6)}.${releaseDate.slice(6, 8)}`;
        return releaseDate;
    };

    const removeArr = () => {
        if (category) return category.replace('[', '').replace(']', '').replaceAll(',', ', ');
        return category;
    };

    return (
        <Box className="headerCover">
            <img className="poster" src={poster || ''} onError={onErrorImg} alt="" />
            <Box>
                <Typography className="title">{nameKR}</Typography>

                <Box className="contentCover">
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{retDate()}</Typography>
                        </Box>
                    )}
                    {category && (
                        <Box className="contentBox">
                            <Typography className="contentType">장르</Typography>
                            <Typography className="content">{removeArr()}</Typography>
                        </Box>
                    )}
                    {reservationRate && (
                        <Box className="contentBox">
                            <Typography className="contentType">예매율</Typography>
                            <Typography className="content">{reservationRate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                    {releaseDate && (
                        <Box className="contentBox">
                            <Typography className="contentType">개봉</Typography>
                            <Typography className="content">{releaseDate}</Typography>
                        </Box>
                    )}
                </Box>
                <Button onClick={onClickBookInfo} className="bookButton">
                    예약하기
                </Button>
            </Box>
        </Box>
    );
};

export default MovieHeader;

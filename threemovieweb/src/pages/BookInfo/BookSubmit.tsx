import React from 'react';
import { Box, Button, Typography } from '@mui/material';
import { useRecoilValue } from 'recoil';
import bookAddrAtom from '../../Recoil/Atom/bookAddrAtom';
import bookTicketPageAtom from '../../Recoil/Atom/bookTicketPageAtom';

const BookSubmit = () => {
    const selectAddr = useRecoilValue(bookAddrAtom);
    const selectTicketPage = useRecoilValue(bookTicketPageAtom);

    const onClickAddr = () => {
        if (selectAddr) window.open(`https://map.naver.com/v5/search/${selectAddr}`);
        else alert('영화, 영화관, 날짜, 시간표를 선택 해주세요.');
    };

    const onClickTicketPage = () => {
        if (selectTicketPage) window.open(`${selectTicketPage}`);
        else alert('영화, 영화관, 날짜, 시간표를 선택 해주세요.');
    };
    return (
        <Box className="submitButtonCover">
            <Button onClick={() => onClickAddr()} className={selectAddr.length > 0 ? 'activeAddr button' : 'button'}>
                <Typography>길찾기</Typography>
            </Button>
            <Button
                onClick={() => onClickTicketPage()}
                className={selectTicketPage.length > 0 ? 'activeTicketPage button' : 'button'}
            >
                <Typography>예약하기</Typography>
            </Button>
        </Box>
    );
};

export default BookSubmit;

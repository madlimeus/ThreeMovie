import React, { useEffect, useState } from 'react';
import { Box, Button, Checkbox, Divider, FormControlLabel, FormGroup, TextField, Typography } from '@mui/material';
import dayjs, { Dayjs } from 'dayjs';
import { DatePicker } from '@mui/x-date-pickers';
import useAxios from '../../../hook/useAxios';
import { checkEmail, checkNickName } from '../../../Util/checkUserInfo';
import '../../../style/scss/_myPage.scss';
import { delCookie, getCookie, setCookie } from '../../../Util/cookieUtil';
import { periodicRefresh } from '../../../Util/refreshToken';
import MyPageInfo from '../../../interfaces/MyPageInfo';
import { locateFindPass } from '../../../Util/locateUtil';

const MyInfoPage = () => {
    const [email, setEmail] = useState<string>('');
    const [existNickName, setExistNickName] = useState<boolean>(false);
    const [nickName, setNickName] = useState<string>('');
    const originNickName = getCookie('NickName');
    const [sex, setSex] = useState<boolean | undefined>(true);
    const [birth, setBirth] = useState<Dayjs | null>(dayjs('2023-04-04'));
    const accessToken = getCookie('AccessToken');

    const getMyPage = useAxios<MyPageInfo>({
        method: 'post',
        url: '/user/mypage',
        config: { headers: { Authorization: `${accessToken}` } },
    });

    useEffect(() => {
        getMyPage[1]();
    }, []);

    useEffect(() => {
        if (getMyPage[0].response) {
            const res = getMyPage[0].response;
            setEmail(res.userEmail);
            setNickName(res.userNickName);
            setSex(res.userSex);
            setBirth(dayjs(res.userBirth));
        }
    }, [getMyPage[0].response]);

    const refetchExistName = useAxios({
        method: 'post',
        url: '/user/nickname/exists',
        data: {
            nickName,
        },
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    const onClickExists = () => {
        refetchExistName[1]();
    };

    useEffect(() => {
        if (refetchExistName[0].response) {
            setExistNickName(true);
        }
    }, [refetchExistName[0].response]);

    const onNickNameChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setNickName(event.target.value);
        setExistNickName(false);
    };

    const onClickMan = () => {
        setSex(true);
    };

    const onClickWoman = () => {
        setSex(false);
    };

    const onCheckMyPage = () => {
        return checkNickName(nickName) && (existNickName || originNickName === nickName);
    };

    const getChangeUserInfo = useAxios({
        method: 'post',
        data: {
            email,
            nickName,
            sex,
            birth,
        },
        url: '/user/info/change',
    });

    const onClickChangeUserInfo = () => {
        getChangeUserInfo[1]();
    };

    useEffect(() => {
        if (getChangeUserInfo[0].response) {
            setCookie('NickName', nickName);
            window.location.href = '/main';
        }
    }, [getChangeUserInfo[0].response]);

    const fetchSignOut = useAxios({
        method: 'post',
        url: '/auth/signout',
        config: { headers: { Authorization: `${accessToken}` } },
    });

    const onClickSignOut = () => {
        fetchSignOut[1]();
    };

    useEffect(() => {
        if (fetchSignOut[0].response) {
            delCookie('AccessToken');
            delCookie('nickName');
            clearTimeout(periodicRefresh());
            localStorage.clear();
            window.location.href = '/main';
        }
    }, [fetchSignOut[0].response]);

    return (
        <Box className="myPageInputCover">
            <Typography className="myPageTitle">마이 페이지</Typography>
            <Box className="myPageBox">
                <TextField
                    label="이메일"
                    className="myPageInput"
                    error={!checkEmail(email)}
                    value={email}
                    size="small"
                    margin="dense"
                    disabled
                />
            </Box>

            <Divider className="divide" flexItem />

            <Box className="myPageBox">
                <TextField
                    label="별명"
                    error={!checkNickName(nickName)}
                    className="myPageNickNameInput"
                    value={nickName}
                    onChange={onNickNameChange}
                    placeholder="별명은 2~8글자로 입력 해주세요."
                    helperText={!checkNickName(nickName) ? '별명은 2~8글자로 입력 해주세요.' : ''}
                    size="small"
                    margin="dense"
                />
                <Button
                    className={
                        checkNickName(nickName) && nickName !== originNickName ? 'myPageButton active' : 'myPageButton'
                    }
                    onClick={onClickExists}
                >
                    중복 확인
                </Button>
            </Box>
            <DatePicker
                className="myPageBirth"
                disableFuture
                format="YYYY-MM-DD"
                label="생일"
                openTo="year"
                views={['year', 'month', 'day']}
                value={birth}
                onChange={(newValue) => {
                    setBirth(newValue);
                }}
            />

            <FormGroup className="sexControlForm" aria-label="성별">
                <FormControlLabel control={<Checkbox checked={sex} onChange={onClickMan} />} label="남성" />
                <FormControlLabel control={<Checkbox checked={!sex} onChange={onClickWoman} />} label="여성" />
            </FormGroup>

            <Button
                className={onCheckMyPage() ? 'myPageConfirmButton active' : 'myPageConfirmButton'}
                onClick={onClickChangeUserInfo}
            >
                수정하기
            </Button>
            <Box className="myPageTextButtonCover">
                <Typography className="myPageTextButton" onClick={onClickSignOut}>
                    회원탈퇴
                </Typography>
                <Divider className="divide" orientation="vertical" flexItem />
                <Typography className="myPageTextButton" onClick={locateFindPass}>
                    비밀번호 변경
                </Typography>
            </Box>
        </Box>
    );
};

export default MyInfoPage;

import React, { useState } from 'react';
import { Box, Button, Checkbox, Divider, FormControlLabel, FormGroup, TextField, Typography } from '@mui/material';
import '../../../style/scss/_signup.scss';
import dayjs, { Dayjs } from 'dayjs';
import { DatePicker } from '@mui/x-date-pickers';
import { checkAuthCode, checkEmail, checkNickName, checkPass, checkPassConfirm } from '../../../Util/checkUserInfo';
import useAxios from '../../../hook/useAxios';

const SignUpPage = () => {
    const [email, setEmail] = useState<string>('');
    const [authCheck, setAuthCheck] = useState<boolean>(false);
    const [authCode, setAuthCode] = useState<string>('');
    const [nickName, setNickName] = useState<string>('');
    const [pass, setPass] = useState<string>('');
    const [passConfirm, setPassConfirm] = useState<string>('');
    const [sex, setSex] = useState<boolean>(true);
    const [birth, setBirth] = useState<Dayjs | null>(dayjs('2023-04-04'));

    const refetchSendMail = useAxios({
        method: 'post',
        url: '/api/mail/auth',
        data: {
            email: { email },
        },
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    const onClickSendMail = () => {
        try {
            refetchSendMail[1]();
        } catch (e) {
            alert(refetchSendMail[0].error);
        }
    };

    const refetchAuthCode = useAxios({
        method: 'post',
        url: '/api/user/auth',
        data: {
            email: { email },
            authCode: { authCode },
        },
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    const onClickAuth = () => {
        try {
            refetchAuthCode[1]();
        } catch (e) {
            alert(refetchAuthCode[0].error);
        }
    };

    const refetchSignUp = useAxios({
        method: 'post',
        url: '/api/user/signup',
        data: {
            email: { email },
            pass: { pass },
        },
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    const onClickSignUp = () => {
        try {
            refetchSignUp[1]();
        } catch (e) {
            alert(refetchSignUp[0].error);
        }
    };

    const onEmailChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const onAuthCode = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setAuthCode(event.target.value);
    };

    const onNickNameChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setNickName(event.target.value);
    };

    const onPasswordChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setPass(event.target.value);
    };

    const onConfirmPasswordChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setPassConfirm(event.target.value);
    };

    const onClickMan = () => {
        setSex(true);
    };

    const onClickWoman = () => {
        setSex(false);
    };

    const onCheckSignUp = () => {
        return (
            checkEmail(email) &&
            checkPass(pass) &&
            checkPassConfirm(pass, passConfirm) &&
            checkNickName(nickName) &&
            authCheck
        );
    };

    return (
        <Box className="signUpInputCover">
            <Typography className="signUpTitle">회원 가입</Typography>
            <Box className="signUpBox">
                <TextField
                    label="이메일"
                    className="signUpInput"
                    error={checkEmail(email)}
                    value={email}
                    onChange={onEmailChange}
                    placeholder="이메일을 입력 해주세요."
                    size="small"
                    margin="dense"
                />
                <Button
                    className={checkEmail(email) ? 'signUpButton active' : 'signUpButton'}
                    onClick={onClickSendMail}
                >
                    메일 전송
                </Button>
            </Box>
            <Box className="signUpBox">
                <TextField
                    label="인증 번호"
                    className="signUpInput"
                    error={checkAuthCode(authCode)}
                    value={authCode}
                    onChange={onAuthCode}
                    placeholder="인증 번호를 입력 해주세요."
                    size="small"
                    margin="dense"
                />
                <Button
                    className={checkAuthCode(authCode) ? 'signUpButton active' : 'signUpButton'}
                    onClick={onClickAuth}
                >
                    인증 확인
                </Button>
            </Box>
            <Divider className="divide" flexItem />

            <TextField
                label="비밀번호"
                className="signUpPassInput"
                value={pass}
                onChange={onPasswordChange}
                placeholder="비밀번호는 8~20자리 대/소/특수 문자가 가능합니다."
                type="password"
                size="small"
                margin="dense"
            />
            <TextField
                label="비밀번호 확인"
                className="signUpPassInput"
                value={passConfirm}
                onChange={onConfirmPasswordChange}
                placeholder="입력하신 비밀번호를 다시 한번 입력 해주세요."
                type="password"
                size="small"
                margin="dense"
            />
            <Divider className="divide" flexItem />

            <TextField
                label="별명"
                className="signUpPassInput"
                value={nickName}
                onChange={onNickNameChange}
                placeholder="별명은 2글자 이상으로 입력 해주세요."
                size="small"
                margin="dense"
            />
            <DatePicker
                className="signUpBirth"
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
                className={onCheckSignUp() ? 'signUpConfirmButton active' : 'signUpConfirmButton'}
                onClick={onClickSignUp}
            >
                가입하기
            </Button>
        </Box>
    );
};

export default SignUpPage;

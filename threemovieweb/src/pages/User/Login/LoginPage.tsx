import React, { useState } from 'react';
import { Box, Button, Divider, Input, InputAdornment, Typography } from '@mui/material';
import '../../../style/scss/_login.scss';
import MailIcon from '@mui/icons-material/Mail';
import PasswordIcon from '@mui/icons-material/Password';
import useAxios from '../../../hook/useAxios';
import { checkEmail, checkPass } from '../../../Util/checkUserInfo';

const LoginPage = () => {
    const [email, setEmail] = useState<string>('');
    const [pass, setPass] = useState<string>('');

    const fetch = () =>
        useAxios({
            method: 'post',
            url: '/api/user/login',
            data: {
                email: { email },
                pass: { pass },
            },
            config: {
                headers: { 'Content-Type': `application/json` },
            },
        });

    const onClickLogin = () => {
        try {
            fetch();
            document.location.href = '/main';
        } catch (e) {
            alert(e);
        }
    };

    const onClickSignUp = () => {
        document.location.href = '/user/signup';
    };

    const onClickPasswordFind = () => {
        document.location.href = '/user/find/pass';
    };

    const onEmailChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const onPasswordChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setPass(event.target.value);
    };

    const onCheckLogin = () => {
        if (checkEmail(email) && checkPass(pass)) {
            return true;
        }
        return false;
    };

    return (
        <Box>
            <Box className="loginCover">
                <Box className="loginBox">
                    <Box className="inputCover">
                        <Input
                            className="loginInput"
                            disableUnderline
                            value={email}
                            onChange={onEmailChange}
                            startAdornment={
                                <InputAdornment position="start">
                                    <MailIcon />
                                </InputAdornment>
                            }
                            placeholder="이메일을 입력 해주세요."
                        />
                        <Input
                            className="loginInput"
                            disableUnderline
                            value={pass}
                            onChange={onPasswordChange}
                            startAdornment={
                                <InputAdornment position="start">
                                    <PasswordIcon />
                                </InputAdornment>
                            }
                            placeholder="비밀번호를 입력 해주세요."
                            type="password"
                        />
                    </Box>
                    <Box className="loginButtonCover">
                        <Button className={onCheckLogin() ? 'loginButton active' : 'loginButton'}>
                            <Typography>로그인</Typography>
                        </Button>
                    </Box>
                </Box>
                <Box className="menuCover">
                    <Box className="signUpFindCover">
                        <Typography className="menuButton" onClick={() => onClickSignUp()}>
                            회원가입
                        </Typography>
                        <Divider className="divide" orientation="vertical" variant="middle" flexItem />
                        <Typography className="menuButton" onClick={() => onClickPasswordFind()}>
                            PW 찾기
                        </Typography>
                    </Box>
                </Box>
            </Box>
        </Box>
    );
};

export default LoginPage;

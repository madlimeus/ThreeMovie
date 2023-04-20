import React, { useEffect, useState } from 'react';
import {
    Box,
    Button,
    Checkbox,
    Divider,
    FormControlLabel,
    FormGroup,
    Input,
    InputAdornment,
    Typography,
} from '@mui/material';
import '../../../style/scss/_login.scss';
import MailIcon from '@mui/icons-material/Mail';
import PasswordIcon from '@mui/icons-material/Password';
import useAxios from '../../../hook/useAxios';
import { checkEmail, checkPass } from '../../../Util/checkUserInfo';
import { periodicRefresh } from '../../../Util/refreshToken';
import { setCookie } from '../../../Util/cookieUtil';

const LoginPage = () => {
    const [email, setEmail] = useState<string>('');
    const [pass, setPass] = useState<string>('');
    const [autoLogin, setAutoLogin] = useState<boolean>(false);

    const fetch = useAxios({
        method: 'post',
        url: '/auth/login',
        data: {
            email,
            password: `${pass}`,
        },
    });

    const onClickLogin = () => {
        fetch[1]();
    };

    const handleOnKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            fetch[1]();
        }
    };

    useEffect(() => {
        if (fetch[0].response) {
            const { accessToken, refreshToken, nickName } = fetch[0].response.data;
            setCookie('AccessToken', `Bearer ${accessToken}`);
            setCookie('NickName', nickName);
            if (autoLogin) {
                localStorage.setItem('refreshToken', `Bearer ${refreshToken}`);
                periodicRefresh();
            }
            window.location.href = '/main';
        }
    }, [fetch[0].response]);

    const onClickAutoLogin = () => {
        setAutoLogin(!autoLogin);
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
                            onKeyDown={handleOnKeyPress}
                        />
                    </Box>
                    <Box className="loginButtonCover">
                        <Button
                            className={onCheckLogin() ? 'loginButton active' : 'loginButton'}
                            onClick={onClickLogin}
                        >
                            <Typography>로그인</Typography>
                        </Button>
                    </Box>
                </Box>
                <Box className="menuCover">
                    <Box className="signUpFindCover">
                        <FormGroup className="autoLoginControlForm">
                            <FormControlLabel
                                control={<Checkbox checked={autoLogin} onChange={onClickAutoLogin} />}
                                label="자동 로그인"
                            />
                        </FormGroup>
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

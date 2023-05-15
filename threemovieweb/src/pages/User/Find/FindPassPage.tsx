import React, { useEffect, useState } from 'react';
import { Box, Button, Divider, TextField, Typography } from '@mui/material';
import useAxios from '../../../hook/useAxios';
import { checkAuthCode, checkEmail, checkPass, checkPassConfirm } from '../../../Util/checkUserInfo';
import timeFormat from '../../../Util/timeFormat';
import '../../../style/scss/_findPass.scss';
import { delCookie } from '../../../Util/cookieUtil';
import { periodicRefresh } from '../../../Util/refreshToken';
import { locateLogin } from '../../../Util/locateUtil';

const FindPassPage = () => {
    const [email, setEmail] = useState<string>('');
    const [authCheck, setAuthCheck] = useState<boolean>(false);
    const [authCode, setAuthCode] = useState<string>('');
    const [pass, setPass] = useState<string>('');
    const [passConfirm, setPassConfirm] = useState<string>('');
    const [time, setTime] = useState(0);
    const [expireAt, setExpireAt] = useState<Date>(new Date());

    // eslint-disable-next-line consistent-return
    useEffect(() => {
        if (time > 0) {
            const Counter = setInterval(() => {
                const gap = Math.floor((expireAt.getTime() - new Date().getTime()) / 1000);
                setTime(300 + gap);
            }, 1000);
            return () => clearInterval(Counter);
        }
    }, [expireAt, time]);

    const refetchSendMail = useAxios({
        method: 'post',
        url: '/auth/mail',
        data: {
            email,
        },
    });

    const onClickSendMail = () => {
        refetchSendMail[1]();
    };

    useEffect(() => {
        if (refetchSendMail[0].response) {
            setTime(300);
            setExpireAt(new Date());
        }
    }, [refetchSendMail[0].response]);

    const refetchAuthCode = useAxios({
        method: 'post',
        url: '/auth/check/code',
        data: {
            email,
            authCode,
        },
        config: {
            headers: { 'Content-Type': `application/json` },
        },
    });

    const onClickAuth = () => {
        refetchAuthCode[1]();
    };

    useEffect(() => {
        if (refetchAuthCode[0].response) setAuthCheck(true);
    }, [refetchAuthCode[0].response]);

    const refetchPassChange = useAxios({
        method: 'post',
        url: '/auth/password/change',
        data: {
            email,
            password: `${pass}`,
        },
    });

    const onClickPassChange = () => {
        refetchPassChange[1]();
    };

    const onEmailChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setEmail(event.target.value);
    };

    const onAuthCode = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setAuthCode(event.target.value);
    };

    const onPasswordChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setPass(event.target.value);
    };

    const onConfirmPasswordChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setPassConfirm(event.target.value);
    };

    const onCheckChangePass = () => {
        return checkEmail(email) && checkPass(pass) && checkPassConfirm(pass, passConfirm) && authCheck;
    };

    useEffect(() => {
        if (refetchPassChange[0].response) {
            delCookie('AccessToken');
            delCookie('nickName');
            clearTimeout(periodicRefresh());
            localStorage.clear();
            locateLogin();
        }
    }, [refetchPassChange[0].response]);

    return (
        <Box className="findPassInputCover">
            <Typography className="findPassTitle">비밀번호 변경</Typography>
            <Box className="findPassBox">
                <TextField
                    label="이메일"
                    className="findPassInput"
                    error={!checkEmail(email)}
                    value={email}
                    onChange={onEmailChange}
                    placeholder="이메일을 입력 해주세요."
                    size="small"
                    margin="dense"
                    disabled={authCheck}
                />
                <Button
                    className={checkEmail(email) ? 'findPassButton active' : 'findPassButton'}
                    onClick={onClickSendMail}
                    disabled={time > 0 || authCheck}
                >
                    메일 전송
                    {time > 0 && timeFormat(time)}
                </Button>
            </Box>
            <Box className="findPassBox">
                <TextField
                    label="인증 번호"
                    className="findPassInput"
                    value={authCode}
                    onChange={onAuthCode}
                    placeholder="인증 번호를 입력 해주세요."
                    size="small"
                    margin="dense"
                    disabled={authCheck}
                />
                <Button
                    className={checkAuthCode(authCode) ? 'findPassButton active' : 'findPassButton'}
                    disabled={authCheck}
                    onClick={onClickAuth}
                >
                    인증 확인
                </Button>
            </Box>
            <Divider className="divide" flexItem />

            {authCheck && (
                <Box>
                    <TextField
                        label="비밀번호"
                        className="findPassInput"
                        error={!checkPass(pass)}
                        value={pass}
                        onChange={onPasswordChange}
                        placeholder="비밀번호는 8~20자리 대/소/특수 문자가 가능합니다."
                        helperText={!checkPass(pass) ? '비밀번호는 8~20자리 대/소/특수 문자로 입력 해주세요.' : ''}
                        type="password"
                        size="small"
                        margin="dense"
                    />
                    <TextField
                        label="비밀번호 확인"
                        className="findPassInput"
                        error={checkPass(pass) && !checkPassConfirm(pass, passConfirm)}
                        value={passConfirm}
                        onChange={onConfirmPasswordChange}
                        placeholder="입력하신 비밀번호를 다시 한번 입력 해주세요."
                        helperText={
                            checkPass(pass) && !checkPassConfirm(pass, passConfirm)
                                ? '입력하신 비밀번호와 다릅니다.'
                                : ''
                        }
                        type="password"
                        size="small"
                        margin="dense"
                    />

                    <Button
                        className={onCheckChangePass() ? 'findPassConfirmButton active' : 'findPassConfirmButton'}
                        onClick={onClickPassChange}
                    >
                        변경하기
                    </Button>
                </Box>
            )}
        </Box>
    );
};

export default FindPassPage;

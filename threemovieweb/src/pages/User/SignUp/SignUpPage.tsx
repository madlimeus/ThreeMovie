import React, {useEffect, useState} from 'react';
import {Box, Button, Checkbox, Divider, FormControlLabel, FormGroup, TextField, Typography} from '@mui/material';
import '../../../style/scss/_signup.scss';
import dayjs, {Dayjs} from 'dayjs';
import {DatePicker} from '@mui/x-date-pickers';
import {checkAuthCode, checkEmail, checkNickName, checkPass, checkPassConfirm} from '../../../Util/checkUserInfo';
import useAxios from '../../../hook/useAxios';
import timeFormat from '../../../Util/timeFormat';
import {locateLogin} from '../../../Util/locateUtil';

const SignUpPage = () => {
	const [email, setEmail] = useState<string>('');
	const [authCheck, setAuthCheck] = useState<boolean>(false);
	const [authCode, setAuthCode] = useState<string>('');
	const [nickName, setNickName] = useState<string>('');
	const [pass, setPass] = useState<string>('');
	const [passConfirm, setPassConfirm] = useState<string>('');
	const [sex, setSex] = useState<boolean>(true);
	const [birth, setBirth] = useState<Dayjs | null>(dayjs('2023-04-04'));
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
		url: '/auth/send/code',
		data: {
			email,
			isSignUp: true
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
			headers: {'Content-Type': `application/json`},
		},
	});
	
	const onClickAuth = () => {
		refetchAuthCode[1]();
	};
	
	useEffect(() => {
		if (refetchAuthCode[0].response) {
			setAuthCheck(true);
			setTime(0);
		}
	}, [refetchAuthCode[0].response]);
	
	const refetchSignUp = useAxios({
		method: 'post',
		url: '/auth/signup',
		data: {
			email,
			password: `${pass}`,
			nickName,
			sex,
			birth,
		},
	});
	
	const onClickSignUp = () => {
		refetchSignUp[1]();
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
	
	useEffect(() => {
		if (refetchSignUp[0].response) locateLogin();
	}, [refetchSignUp[0].response]);
	
	return (
		<Box className="signUpInputCover">
			<Typography className="signUpTitle">회원 가입</Typography>
			<Box className="signUpBox">
				<TextField
					label="이메일"
					className="signUpInput"
					error={!checkEmail(email)}
					value={email}
					onChange={onEmailChange}
					placeholder="이메일을 입력 해주세요."
					size="small"
					margin="dense"
					disabled={authCheck}
				/>
				<Button
					className={checkEmail(email) ? 'signUpButton active' : 'signUpButton'}
					onClick={onClickSendMail}
					disabled={time > 0 || authCheck}
				>
					메일 전송
					{time > 0 && timeFormat(time)}
				</Button>
			</Box>
			<Box className="signUpBox">
				<TextField
					label="인증 번호"
					className="signUpInput"
					error={!checkAuthCode(authCode)}
					value={authCode}
					onChange={onAuthCode}
					placeholder="인증 번호를 입력 해주세요."
					size="small"
					margin="dense"
					disabled={authCheck}
				/>
				<Button
					className={checkAuthCode(authCode) ? 'signUpButton active' : 'signUpButton'}
					disabled={authCheck}
					onClick={onClickAuth}
				>
					인증 확인
				</Button>
			</Box>
			<Divider className="divide" flexItem/>
			
			<TextField
				label="비밀번호"
				className="signUpPassInput"
				error={!checkPass(pass)}
				value={pass}
				onChange={onPasswordChange}
				placeholder="비밀번호는 8~20자리 숫자/대/소/특수 문자가 가능합니다."
				helperText={!checkPass(pass) ? '비밀번호는 8~20자리 숫자/대/소/특수 문자로 입력 해주세요.' : ''}
				type="password"
				size="small"
				margin="dense"
			/>
			<TextField
				label="비밀번호 확인"
				className="signUpPassInput"
				error={checkPass(pass) && !checkPassConfirm(pass, passConfirm)}
				value={passConfirm}
				onChange={onConfirmPasswordChange}
				placeholder="입력하신 비밀번호를 다시 한번 입력 해주세요."
				helperText={
					checkPass(pass) && !checkPassConfirm(pass, passConfirm) ? '입력하신 비밀번호와 다릅니다.' : ''
				}
				type="password"
				size="small"
				margin="dense"
			/>
			<Divider className="divide" flexItem/>
			
			<TextField
				label="별명"
				error={!checkNickName(nickName)}
				className="signUpPassInput"
				value={nickName}
				onChange={onNickNameChange}
				placeholder="별명은 2글자 이상으로 입력 해주세요."
				helperText="2~8 글자로 입력 해주세요."
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
				<FormControlLabel control={<Checkbox checked={sex} onChange={onClickMan}/>} label="남성"/>
				<FormControlLabel control={<Checkbox checked={!sex} onChange={onClickWoman}/>} label="여성"/>
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

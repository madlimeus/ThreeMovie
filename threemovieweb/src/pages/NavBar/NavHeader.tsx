import React, {useEffect} from 'react';
import {Box, Button, Typography} from '@mui/material';
import Logo from '../../assets/images/MTLogo.png';
import {periodicRefresh} from '../../Util/refreshToken';
import useAxios from '../../hook/useAxios';
import {delCookie, getCookie} from '../../Util/cookieUtil';
import {locateLogin, locateMain, locateMyPage} from '../../Util/locateUtil';
import VerticalDivider from "../../layout/VerticalDivider";
import TextButton from "../../layout/TextButton";
import NavSearch from "./NavSearch";

const NavHeader = () => {
	const nickName = getCookie('NickName');
	const accessToken = getCookie('AccessToken');
	
	const fetchLogOut = useAxios({
		method: 'post',
		url: '/auth/logout',
		config: {headers: {Authorization: `${accessToken}`}},
	});
	
	const onClickLogout = () => {
		fetchLogOut[1]();
		console.log(localStorage.get('RefreshToken'));
	};
	
	useEffect(() => {
		if (fetchLogOut[0].response) {
			delCookie('AccessToken');
			delCookie('NickName');
			clearTimeout(periodicRefresh());
			localStorage.clear();
			
			if (window.location.href.includes('mypage')) locateMain();
			else window.location.reload();
		}
	}, [fetchLogOut[0].response]);
	
	
	return (
		<Box className="headmenu">
			<NavSearch/>
			<Button className="logo" onClick={locateMain} disableElevation disableRipple>
				<img src={Logo} alt=""/>
			</Button>
			
			{nickName ? (
				<Box className="loginHeader">
					<Typography className="nickName">{nickName}님</Typography>
					<Box className="loginMenu">
						<TextButton text="마이페이지" onClick={locateMyPage}/>
						
						<VerticalDivider/>
						<TextButton text="로그아웃" onClick={onClickLogout}/>
					</Box>
				</Box>
			) : (
				<Button className="account" onClick={locateLogin}>
					<TextButton text="로그인" onClick={locateLogin}/>
				</Button>
			)}
		</Box>
	);
};

export default NavHeader;

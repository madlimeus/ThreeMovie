import React, { useEffect, useState } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import { Box, Button, Divider, Input, InputAdornment, Typography } from '@mui/material';
import Logo from '../../assets/images/MTLogo.png';
import { periodicRefresh } from '../../Util/refreshToken';
import useAxios from '../../hook/useAxios';
import { delCookie, getCookie } from '../../Util/cookieUtil';
import { locateLogin, locateMain, locateMyPage } from '../../Util/locateUtil';

const NavHeader = () => {
    const [onSearch, setOnSearch] = useState<boolean>(false);
    const [search, setSearch] = useState<string>('');
    const nickName = getCookie('NickName');
    const accessToken = getCookie('AccessToken');

    const fetchLogOut = useAxios({
        method: 'post',
        url: '/auth/logout',
        config: { headers: { Authorization: `${accessToken}` } },
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

    const onClickSearch = () => {
        setOnSearch(true);
    };

    const onChangeSearch = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
        setSearch(event.target.value);
    };

    const handleOnKeyPress = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === 'Enter') {
            setOnSearch(false);
        }
    };

    return (
        <Box className="headmenu">
            <Box className="searchBox">
                <Input
                    disableUnderline
                    className={onSearch ? 'searchArea active' : 'searchArea'}
                    placeholder="영화 정보를 입력 해주세요."
                    endAdornment={
                        <InputAdornment position="end">
                            <SearchIcon />
                        </InputAdornment>
                    }
                    value={search}
                    onChange={onChangeSearch}
                    inputProps={{
                        maxLength: 30,
                    }}
                    onClick={onClickSearch}
                    onKeyDown={handleOnKeyPress}
                />
            </Box>
            <Button className="logo" onClick={locateMain} disableElevation disableRipple>
                <img src={Logo} alt="" />
            </Button>

            {nickName ? (
                <Box className="loginHeader">
                    <Typography className="nickName">{nickName}님</Typography>
                    <Box className="loginMenu">
                        <Typography className="menuButton" onClick={locateMyPage}>
                            마이페이지
                        </Typography>

                        <Divider className="divide" orientation="vertical" flexItem />
                        <Typography className="menuButton" onClick={() => onClickLogout()}>
                            로그아웃
                        </Typography>
                    </Box>
                </Box>
            ) : (
                <Button className="account" onClick={locateLogin}>
                    <Typography>로그인</Typography>
                </Button>
            )}
        </Box>
    );
};

export default NavHeader;

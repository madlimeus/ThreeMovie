import React from 'react';
import {Box, Divider} from '@mui/material';
import {useRecoilValue} from "recoil";
import {Outlet, useLocation} from 'react-router-dom';
import '../../style/scss/_flexbox.scss';
import '../../style/scss/_navbar.scss';
import loadingAtom from "../../Recoil/Atom/loadingAtom";
import Loading from "../Loading";
import NavHeader from './NavHeader';
import NavMenu from './NavMenu';

const NavBarPage = () => {
	const loading = useRecoilValue(loadingAtom);
	const location = useLocation();
	
	return (
		<Box className="flexbox">
			{location.pathname.search('user') !== -1 && <Box className="backgroundImage"/>}
			{loading > 0 && <Loading/>}
			<Box className="coverHeader">
				<NavHeader/>
				<Divider className="divide"/>
				<NavMenu/>
			</Box>
			<Outlet/>
		</Box>
	);
};

export default NavBarPage;

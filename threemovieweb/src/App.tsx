import React, {useEffect} from 'react';
import {ThemeProvider} from '@mui/material';
import {useRecoilState} from 'recoil';
import AppRouter from './Routes/AppRouter';
import typotheme from './style/typotheme';
import autoLoginAtom from './Recoil/Atom/autoLoginAtom';
import {periodicRefresh} from './Util/refreshToken';


const App = () => {
	const [autoLogin, setAutoLogin] = useRecoilState(autoLoginAtom);
	useEffect(() => {
		if (autoLogin) {
			const refreshToken = localStorage.getItem('refreshToken');
			if (refreshToken) {
				periodicRefresh();
			} else {
				localStorage.clear();
			}
			setAutoLogin(false);
		}
	}, []);
	
	return (
		<ThemeProvider theme={typotheme}>
			<AppRouter/>
		</ThemeProvider>
	);
};

export default App;

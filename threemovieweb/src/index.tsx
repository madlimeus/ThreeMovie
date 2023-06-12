import React from 'react';
import ReactDOM from 'react-dom/client';
import {RecoilRoot} from 'recoil';
import {ApolloProvider} from '@apollo/react-hooks';
import {koKR, LocalizationProvider} from '@mui/x-date-pickers';
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
import 'dayjs/locale/ko';
import {CookiesProvider} from 'react-cookie';
import App from './App';
import {client} from './apollo/client';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
	<CookiesProvider>
		<ApolloProvider client={client}>
			<LocalizationProvider
				dateAdapter={AdapterDayjs}
				adapterLocale="ko"
				localeText={koKR.components.MuiLocalizationProvider.defaultProps.localeText}
			>
				<RecoilRoot>
					<App/>
				</RecoilRoot>
			</LocalizationProvider>
		</ApolloProvider>
	</CookiesProvider>,
);

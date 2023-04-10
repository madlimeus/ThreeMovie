import React from 'react';
import ReactDOM from 'react-dom/client';
import { RecoilRoot } from 'recoil';
import { ApolloProvider } from '@apollo/react-hooks';
import App from './App';
import { client } from './apollo/client';

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
    <ApolloProvider client={client}>
        <RecoilRoot>
            <React.StrictMode>
                <App />
            </React.StrictMode>
        </RecoilRoot>
    </ApolloProvider>,
);

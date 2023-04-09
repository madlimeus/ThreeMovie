import { createGlobalStyle } from 'styled-components';
import reset from 'styled-reset';

const GlobalStyles = createGlobalStyle`
    ${reset}
    * {
    	margin: 0;
        padding: 0;
        box-sizing: border-box;
    }
    body {
        align-items: center;
        background: black;
        display: flex;
        flex-direction: column;
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        color: white;
        font-family: 'GothicA1-Light';
        font-color: white;
        -webkit-user-select:none;
        -moz-user-select:none;
        -ms-user-select:none;
        user-select:none
    }
    button {
        cursor: pointer;
    }
    
    @font-face {
        font-family: 'GothicA1-Light';
        src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2205@1.0/GothicA1-Light.woff2') format('woff2');
        font-weight: 300;
        font-style: normal;
    }
`;

export default GlobalStyles;

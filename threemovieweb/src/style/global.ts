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
        min-width: 1024px;
        max-width: 1920px;
        min-height: 768px;
        max-height: 1080px;
        align-items: center;
        background: black;
        display: flex;
        flex-direction: column;
        height: 100vh;
        width: 100vw;
        margin: 0;
        padding: 0;
        transition: all 0.25s linear;
        color: white;
    }
    button { 
        cursor: pointer;
    }
`;

export default GlobalStyles;

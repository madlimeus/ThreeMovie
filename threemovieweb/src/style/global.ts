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
        margin: 0;
        padding: 0;
        color: white;
    }
    button { 
        cursor: pointer;
    }
`;

export default GlobalStyles;

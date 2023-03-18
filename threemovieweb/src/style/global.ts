import { createGlobalStyle } from 'styled-components';

const GlobalStyles = createGlobalStyle`
    html{
        minWidth: "1024px",
        minHeight: "768px",
        width: "100%",
        height: "100%",
        
        body{
            background: "black",
            padding: 0;
            margin: 0;
            @font-face{
                font-family: NanumSquareRoundB;
                src:url('../assets/font/NanumSquareRoundB.ttf') format('truetype');
            }
        };
    }
    `;

export default GlobalStyles;

import axios from 'axios';
import { setCookie } from './cookieUtil';

const onSilentRefresh = () => {
    console.log('실행됨');
    const refreshToken = localStorage.getItem('RefreshToken');
    axios
        .post('/reissue', {}, { headers: { Authorization: `bearer ${refreshToken}` } })
        .then((res) => {
            const accessToken = res.data;
            console.log(res);
            setCookie('AccessToken', `Bearer ${accessToken}`);
        })
        .catch((error) => {
            clearTimeout(periodicRefresh());
            alert('다시 로그인 해주세요.');
        });
    if (performance.navigation.type === 1) {
        onSilentRefresh();
    }
};

export const periodicRefresh = () => setTimeout(onSilentRefresh, 2 * 60 * 60 * 1000);

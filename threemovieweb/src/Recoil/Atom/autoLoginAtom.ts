import { atom } from 'recoil';

const autoLoginAtom = atom<boolean>({
    key: 'autoLogin',
    default: true,
});

export default autoLoginAtom;

import { atom } from 'recoil';

const loadingAtom = atom<string[]>({
    key: 'loading',
    default: [],
});

export default loadingAtom;

import { atom } from 'recoil';

const movieNowAtom = atom<string>({
    key: 'mainMovie',
    default: '',
});

export default movieNowAtom;

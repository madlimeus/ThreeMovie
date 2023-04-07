import { atom } from 'recoil';

const bookBrchFilterAtom = atom<string[]>({
    key: 'bookBrchFilter',
    default: [],
});

export default bookBrchFilterAtom;

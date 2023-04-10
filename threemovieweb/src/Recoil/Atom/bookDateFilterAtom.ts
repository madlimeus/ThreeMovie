import { atom } from 'recoil';

const bookDateFilterAtom = atom<string[]>({
    key: 'bookDateFilter',
    default: [],
});

export default bookDateFilterAtom;

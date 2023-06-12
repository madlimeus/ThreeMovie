import {atom} from 'recoil';

const bookDateFilterAtom = atom<number[]>({
	key: 'bookDateFilter',
	default: [],
});

export default bookDateFilterAtom;

import { atom } from 'recoil';

const bookMovieFilterAtom = atom<string[]>({
    key: 'bookMovieFilter',
    default: [],
});

export default bookMovieFilterAtom;

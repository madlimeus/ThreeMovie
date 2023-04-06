import { atom } from 'recoil';

const bookMovieTheaterFilterAtom = atom<string[]>({
    key: 'bookMovieTheaterFilter',
    default: [],
});

export default bookMovieTheaterFilterAtom;

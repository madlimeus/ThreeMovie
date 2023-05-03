import { atom } from 'recoil';
import MovieData from '../../interfaces/MovieData';

const movieListAtom = atom<MovieData[]>({
    key: 'movieList',
    default: [],
});

export default movieListAtom;

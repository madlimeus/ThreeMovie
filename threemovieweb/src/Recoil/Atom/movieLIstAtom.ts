import { atom } from 'recoil';
import Movieinfo from '../../interfaces/Movieinfo';

const movieListAtom = atom<Movieinfo[]>({
    key: 'movieList',
    default: [],
});

export default movieListAtom;

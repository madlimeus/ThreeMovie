import { selector } from 'recoil';
import movieListAtom from '../Atom/movieLIstAtom';
import movieNowAtom from '../Atom/movieNowAtom';

const movieInfoSelector = selector({
    key: 'movieInfoSelector',
    get: ({ get }) => {
        const movieList = get(movieListAtom);
        const mainMovie = get(movieNowAtom);
        const movieInfo = movieList.filter((movieInfo) => movieInfo.movieId === mainMovie);

        return movieInfo[0];
    },
});

export default movieInfoSelector;

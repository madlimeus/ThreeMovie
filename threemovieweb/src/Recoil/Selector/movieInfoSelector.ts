import { selector } from 'recoil';
import movieListAtom from '../Atom/movieLIstAtom';
import movieNowAtom from '../Atom/movieNowAtom';

const movieInfoSelector = selector({
    key: 'movieInfoSelector',
    get: ({ get }) => {
        const movieList = get(movieListAtom);
        const mainMovie = get(movieNowAtom);
        const movieInfo = movieList.filter((movieInfo) => movieInfo.movieId === mainMovie);

        console.log(movieInfo[0]);
        console.log(Array < string > movieInfo[0].steelcuts);
        return movieInfo[0];
    },
});

export default movieInfoSelector;

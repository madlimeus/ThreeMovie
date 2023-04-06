import { useRecoilValue } from 'recoil';
import bookDateFilterAtom from '../../Recoil/Atom/bookDateFilter';
import bookMovieFilterAtom from '../../Recoil/Atom/bookMovieFilter';
import bookBrchFilterAtom from '../../Recoil/Atom/bookBrchFilter';
import Filter from '../../interfaces/Filter';
import bookMovieTheaterFilterAtom from '../../Recoil/Atom/bookMovieTheaterFilter';

const retFilter = () => {
    const dateFilter = useRecoilValue<string[]>(bookDateFilterAtom);
    const movieFilter = useRecoilValue<string[]>(bookMovieFilterAtom);
    const movieTheaterFilter = useRecoilValue<string[]>(bookMovieTheaterFilterAtom);
    const brchFilter = useRecoilValue<string[]>(bookBrchFilterAtom);

    const ret: Filter = { movieFilter, movieTheaterFilter, brchFilter, dateFilter };

    return ret;
};

export default retFilter;

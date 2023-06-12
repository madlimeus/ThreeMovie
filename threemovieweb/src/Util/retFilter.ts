import {useRecoilValue} from 'recoil';
import bookDateFilterAtom from '../Recoil/Atom/bookDateFilterAtom';
import bookMovieFilterAtom from '../Recoil/Atom/bookMovieFilterAtom';
import bookBrchFilterAtom from '../Recoil/Atom/bookBrchFilterAtom';
import Filter from '../interfaces/Filter';
import bookMovieTheaterFilterAtom from '../Recoil/Atom/bookMovieTheaterFilterAtom';

const retFilter = () => {
	const dateFilter = useRecoilValue<number[]>(bookDateFilterAtom);
	const movieFilter = useRecoilValue<string[]>(bookMovieFilterAtom);
	const movieTheaterFilter = useRecoilValue<string[]>(bookMovieTheaterFilterAtom);
	const brchFilter = useRecoilValue<string[]>(bookBrchFilterAtom);
	
	const ret: Filter = {movieFilter, movieTheaterFilter, brchFilter, dateFilter};
	
	return ret;
};

export default retFilter;

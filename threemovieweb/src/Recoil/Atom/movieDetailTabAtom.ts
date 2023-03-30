import {atom} from 'recoil';

const movieDetailTabAtom = atom<string>({
	key: 'movieDetailTab',
	default: 'review',
});

export default movieDetailTabAtom;

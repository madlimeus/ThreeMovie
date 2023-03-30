import {atom} from 'recoil';

const movieDetailTabAtom = atom<string>({
	key: 'movieDetailTab',
	default: 'detail',
});

export default movieDetailTabAtom;

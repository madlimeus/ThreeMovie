import { atom } from 'recoil';

const bookAddrAtom = atom<string>({
    key: 'bookAddr',
    default: '',
});

export default bookAddrAtom;

import { atom } from 'recoil';

const bookTicketPageAtom = atom<string>({
    key: 'bookTicketPage',
    default: '',
});

export default bookTicketPageAtom;

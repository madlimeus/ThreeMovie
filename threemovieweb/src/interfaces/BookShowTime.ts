export interface BookShowTime {
    movieKR: string;
    movieTheater: string;
    brchKR: string;
    brchEN: string | undefined;
    date: string;
    totalSeat: number;
    playKind: string | undefined;
    screenKR: string | undefined;
    screenEN: string | undefined;
    addrKR: string;
    addrEN: string | undefined;
    items: ShowTimeItem[];
}

export interface ShowTimeItem {
    ticketPage: string;
    startTime: string;
    endTime: string;
    restSeat: string;
}

export interface BookBrch {
    city: string;
    items: TheaterItem[];
}

export interface TheaterItem {
    movieTheater: string;
    brchKR: string;
    brchEN: string | undefined;
    addrKR: string;
    addrEN: string | undefined;
}

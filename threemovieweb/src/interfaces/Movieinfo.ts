interface Movieinfo {
    movieId: string;
    netizenAvgRate: number;
    reservationRate: number;
    nameKR: string;
    nameEN: string;
    poster: string | null;
    category: string;
    steelcuts: string[] | null;
    trailer: string[] | null;
}

export default Movieinfo;

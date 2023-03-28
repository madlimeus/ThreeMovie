interface MovieDetail {
    movieId: string;
    netizenAvgRate: number;
    reservationRate: number;
    summary: string;
    nameKR: string;
    nameEN: string;
    releaseDate: string;
    poster: string | null;
    category: string;
    steelcuts: string | null;
    trailer: string | null;
    // creator info
    itmes: string;
}

export default MovieDetail;

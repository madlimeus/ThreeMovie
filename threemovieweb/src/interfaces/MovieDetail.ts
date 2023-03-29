interface MovieDetail {
    movieId: string;
    netizenAvgRate: number;
    reservationRate: number;
    summary: string;
    nameKR: string;
    nameEN: string;
    releaseDate: string;
    poster: string | undefined;
    category: string;
    steelcuts: string | undefined;
    trailer: string | undefined;
    // creator info
    itmes: string;
}

export default MovieDetail;

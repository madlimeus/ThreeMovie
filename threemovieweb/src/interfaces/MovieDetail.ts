interface MovieDetail {
    movieId: string;
    netizenAvgRate: number;
    reservationRate: number;
    runningTime: number | undefined;
    admissionCode: string | undefined;
    country: string | undefined;
    reservationRank: string | undefined;
    totalAudience: string | undefined;
    summary: string | undefined;
    makingNote: string | undefined;
    nameKR: string;
    nameEN: string;
    releaseDate: string;
    poster: string | undefined;
    category: string;
    steelcuts: string | undefined;
    trailer: string | undefined;
    // creator info
    items: string;
}

export default MovieDetail;

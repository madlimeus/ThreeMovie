interface BookMovie {
    movieId: string;
    movieKR: string;
    movieEN: string | undefined;
    category: string | undefined;
    runningTime: string | undefined;
    country: string | undefined;
    poster: string | undefined;
    reservationRank: string | undefined;
}

export default BookMovie;

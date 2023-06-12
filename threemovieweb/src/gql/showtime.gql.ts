import {gql} from 'graphql-tag';

export const GET_MOVIE_LIST = gql`
    query {
        getMovieList {
            movieId
            movieKr
            movieEn
            category
            runningTime
            poster
            reservationRank
        }
    }
`;

export const GET_THEATER_LIST = gql`
    query ($filter: Filter) {
        getTheaterList(filter: $filter) {
            city
            items {
                movieTheater
                brchKr
                brchEn
                addrKr
                addrEn
            }
        }
    }
`;

export const GET_DATE_LIST = gql`
    query ($filter: Filter) {
        getDateList(filter: $filter) {
            date
        }
    }
`;

export const GET_SHOW_TIME_LIST = gql`
    query ($filter: Filter) {
        getShowTimeList(filter: $filter) {
            movieKr
            movieTheater
            brchKr
            brchEn
            date
            totalSeat
            playKind
            screenKr
            screenEn
            addrKr
            addrEn
            res {
                startTime
                endTime
                restSeat
                ticketPage
            }
        }
    }
`;

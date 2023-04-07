import { gql } from 'graphql-tag';

export const GET_MOVIE_LIST = gql`
    query {
        getMovieList {
            movieId
            movieKR
            movieEN
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
                brchKR
                brchEN
                addrKR
                addrEN
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
            movieKR
            movieTheater
            brchKR
            brchEN
            date
            totalSeat
            playKind
            screenKR
            screenEN
            addrKR
            addrEN
            items {
                ticketPage
                startTime
                endTime
                restSeat
            }
        }
    }
`;

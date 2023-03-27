package com.threemovie.threemovieapi.service

interface MovieDataControlService {
    fun GET_MOVIE_INFO_DAUM()
    fun GET_MOVIE_INFO_DAUM_for_upcoming()
    fun truncateAllMovieData()
}
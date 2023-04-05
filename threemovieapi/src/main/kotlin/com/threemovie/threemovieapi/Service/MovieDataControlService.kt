package com.threemovie.threemovieapi.Service

interface MovieDataControlService {
    fun GET_MOVIE_INFO_DAUM()
    fun GET_MOVIE_INFO_DAUM_for_upcoming()
    fun truncateAllMovieData()
}

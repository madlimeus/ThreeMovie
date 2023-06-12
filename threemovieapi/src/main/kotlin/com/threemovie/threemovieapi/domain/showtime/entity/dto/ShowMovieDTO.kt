package com.threemovie.threemovieapi.domain.showtime.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class ShowMovieDTO @QueryProjection constructor(
	val movieId: String = "",
	
	val movieKr: String = " ",
	
	val movieEn: String? = null,
	
	val category: String? = null,
	
	val runningTime: String? = null,
	
	val country: String? = null,
	
	val poster: String? = null,
	
	val reservationRank: String? = null,
)

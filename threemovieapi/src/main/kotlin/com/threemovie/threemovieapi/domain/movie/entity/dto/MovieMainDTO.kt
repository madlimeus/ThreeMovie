package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class MovieMainDTO @QueryProjection constructor(
	val movieId: String = "",
	
	val netizenAvgRate: Double = 0.0,
	
	val reservationRate: Double = 0.0,
	
	val nameKr: String = "",
	
	val nameEn: String? = "",
	
	val poster: String? = null,
	
	val category: String? = "",
	
	val previews: Set<MoviePreviewDTO>,
)

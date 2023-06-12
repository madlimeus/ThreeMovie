package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class MovieDetailDTO @QueryProjection constructor(
	val movieId: String = "",
	
	val netizenAvgRate: Double = 0.0,
	
	val reservationRate: Double = 0.0,
	
	val runningTime: String? = "",
	
	val admissionCode: String? = "",
	
	val country: String? = "",
	
	val reservationRank: String? = "",
	
	val totalAudience: String? = "",
	
	val summary: String = "",
	
	val makingNote: String? = null,
	
	val nameKr: String = "",
	
	val nameEn: String = "",
	
	val releaseDate: Long = 202304050607,
	
	val poster: String? = null,
	
	val category: String = "",
	
	val creators: Set<MovieCreatorDTO>?,
	
	val reviews: Set<MovieReviewDTO>?,
	
	val previews: Set<MoviePreviewDTO>?,
)

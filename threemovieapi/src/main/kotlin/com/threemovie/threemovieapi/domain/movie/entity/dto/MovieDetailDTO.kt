package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieReview

data class MovieDetailDTO @QueryProjection constructor(
	val movieId: String = "",
	
	val netizenAvgRate: Double = 0.0,
	
	val reservationRate: Double = 0.0,
	
	val summary: String = "",
	
	val makingNote: String? = null,
	
	val nameKR: String = "",
	
	val nameEN: String = "",
	
	val releaseDate: String? = null,
	
	val poster: String? = null,
	
	val category: String = "",
	
	val steelcuts: String? = null,
	
	val trailer: String? = null,
	
	val nameKr: String,
	
	val nameEn: String,
	
	val roleKr: String,
	
	val link: String,
	
	val reviews: List<MovieReview>
)

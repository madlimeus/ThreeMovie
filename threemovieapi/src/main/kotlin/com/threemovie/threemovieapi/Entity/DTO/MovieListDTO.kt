package com.threemovie.threemovieapi.Entity.DTO

import com.querydsl.core.annotations.QueryProjection

data class MovieListDTO @QueryProjection constructor(
	val movieId: String = "",

	val netizenAvgRate: Double = 0.0,

	val reservationRate: Double = 0.0,

	val nameKR: String = "",

	val nameEN: String = "",

	val poster: String? = null,

	val category: String = "",

	val steelcuts: String? = null,

	val trailer: String? = null,
)

package com.threemovie.threemovieapi.Entity.DTO

import com.querydsl.core.annotations.QueryProjection

data class MovieDetailDTO @QueryProjection constructor(
	val movieId: String = "",

	val netizenAvgRate: Double = 0.0,

	val reservationRate: Double = 0.0,

	val summary: String = "",

	val nameKR: String = "",

	val nameEN: String = "",

	val releaseDate: String? = null,

	val poster: String? = null,

	val category: String = "",

	val makingNote: String? = null,

	val steelcuts: String? = null,

	val trailer: String? = null,

	val items: String = "",

	)

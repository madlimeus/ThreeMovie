package com.threemovie.threemovieapi.Entity.DTO

import com.querydsl.core.annotations.QueryProjection

data class MovieDetailDTO @QueryProjection constructor(
	val movieId: String = "",

	val summary: String = "",

	val nameKR: String = "",

	val nameEN: String = "",

	val releaseDate: String? = "",

	val poster: String? = "",

	val category: String = "",

	val steelcuts: String = "",

	val trailer: String = "",

	val director: String = "",

	val actor: String = "",

	val photoAddress: String = "",
)

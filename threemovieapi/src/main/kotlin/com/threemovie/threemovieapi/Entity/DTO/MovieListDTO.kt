package com.threemovie.threemovieapi.Entity.DTO

import com.querydsl.core.annotations.QueryProjection

data class MovieListDTO @QueryProjection constructor(
	val MovieId: String = "",

	val Summary: String = "",

	val NameKR: String = "",

	val NameEN: String = "",

	val Poster: String? = "",

	val Category: String = "",

	val Steelcuts: String = "",

	val Trailer: String = "",
)

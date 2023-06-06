package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class MovieNameDTO @QueryProjection constructor(
	val movieId: String = "",
	
	val nameKr: String = "",
)

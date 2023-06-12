package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class MovieCreatorDTO @QueryProjection constructor(
	val nameKr: String = "",
	
	val nameEn: String? = null,
	
	val roleKr: String? = null,
	
	val link: String? = null,
)

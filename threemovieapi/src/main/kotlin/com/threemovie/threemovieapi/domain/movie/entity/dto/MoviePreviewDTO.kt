package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class MoviePreviewDTO @QueryProjection constructor(
	var type: String? = "",
	
	var link: String? = "",
)

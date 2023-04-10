package com.threemovie.threemovieapi.Entity.DTO

import com.querydsl.core.annotations.QueryProjection

data class ShowDateDTO @QueryProjection constructor(
	val date: String? = null,
)

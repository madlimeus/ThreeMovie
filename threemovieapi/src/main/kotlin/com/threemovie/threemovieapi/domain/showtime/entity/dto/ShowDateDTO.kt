package com.threemovie.threemovieapi.domain.showtime.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class ShowDateDTO @QueryProjection constructor(
	val date: Long = 202304050603,
)

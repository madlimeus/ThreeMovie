package com.threemovie.threemovieapi.domain.movie.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class MovieReviewDTO @QueryProjection constructor(
	val review: String? = null,
	
	val date: Long = 2020305060503,
	
	val recommendation: Int = 0,
	
	val movieTheater: String = "mt",
)

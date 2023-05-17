package com.threemovie.threemovieapi.domain.showtime.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class ShowTheaterDTO @QueryProjection constructor(
	val movieTheater: String = "MT",
	
	val brchKR: String = "본점",
	
	val brchEN: String? = null,
	
	val city: String = "신대방",
	
	val addrKR: String = "삼거리",
	
	val addrEN: String? = "null",
)

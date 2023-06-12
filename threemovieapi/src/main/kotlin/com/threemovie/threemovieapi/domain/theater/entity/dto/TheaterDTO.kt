package com.threemovie.threemovieapi.domain.theater.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class TheaterDTO @QueryProjection constructor(
	val movieTheater: String = "MT",
	
	val city: String = "서울",
	
	val brchKr: String = "",
	
	val brchEn: String? = null,
	
	val addrKr: String = "",
	
	val addrEn: String? = null,
)

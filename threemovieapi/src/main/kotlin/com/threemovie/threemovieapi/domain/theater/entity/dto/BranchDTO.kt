package com.threemovie.threemovieapi.domain.theater.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class BranchDTO @QueryProjection constructor(
	val brchKr: String = "",
	
	val brchEn: String? = null,
	
	val addrKr: String = "",
	
	val addrEn: String? = null,
)

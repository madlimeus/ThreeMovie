package com.threemovie.threemovieapi.Entity.DTO

import com.querydsl.core.annotations.QueryProjection

data class ShowTimeItemDTO @QueryProjection constructor(
	val totalSeat: Int = 120,
	
	val playKind: String = "0120",
	
	val screenKR: String = "0120",
	
	val screenEN: String? = null,
	
	val item: String = "none",
)

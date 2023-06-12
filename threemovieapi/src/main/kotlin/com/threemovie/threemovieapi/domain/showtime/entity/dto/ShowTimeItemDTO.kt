package com.threemovie.threemovieapi.domain.showtime.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class ShowTimeItemDTO @QueryProjection constructor(
	val movieKr: String = "MT제작기",
	
	val poster: String? = "",
	
	val movieTheater: String = "MT",
	
	val brchKr: String = "신대방",
	
	val brchEn: String? = null,
	
	val date: Long = 202304050607,
	
	val totalSeat: Int = 120,
	
	val playKind: String = "0120",
	
	val screenKr: String = "0120",
	
	val screenEn: String? = null,
	
	val addrKr: String = "신대방동",
	
	val addrEn: String? = null,
	
	val res: List<ShowTimeReserveDTO>,
)

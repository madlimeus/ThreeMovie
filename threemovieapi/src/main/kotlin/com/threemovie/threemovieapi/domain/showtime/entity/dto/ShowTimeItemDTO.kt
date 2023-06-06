package com.threemovie.threemovieapi.domain.showtime.entity.dto

import com.querydsl.core.annotations.QueryProjection
import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTimeReserve

data class ShowTimeItemDTO @QueryProjection constructor(
	val movieKr: String = "MT제작기",
	
	val movieTheater: String = "MT",
	
	val brchKr: String = "신대방",
	
	val brchEn: String? = null,
	
	val date: String = "1998-01-20",
	
	val totalSeat: Int = 120,
	
	val playKind: String = "0120",
	
	val screenKr: String = "0120",
	
	val screenEn: String? = null,
	
	val addrKr: String = "신대방동",
	
	val addrEn: String? = null,
	
	val items: List<ShowTimeReserve>,
)

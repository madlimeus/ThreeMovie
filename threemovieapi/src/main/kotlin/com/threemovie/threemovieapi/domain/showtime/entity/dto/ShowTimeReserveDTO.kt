package com.threemovie.threemovieapi.domain.showtime.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class ShowTimeReserveDTO @QueryProjection constructor(
	val startTime: Long = 0L,
	
	val endTime: Long = 0L,
	
	val restSeat: Int = 0,
	
	val ticketPage: String = "",
)

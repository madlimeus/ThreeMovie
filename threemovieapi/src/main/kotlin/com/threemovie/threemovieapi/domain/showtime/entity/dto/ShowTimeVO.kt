package com.threemovie.threemovieapi.domain.showtime.entity.dto

data class ShowTimeVO(
	val screemEN: String = "",
	
	val totalSeat: Int = 200,

//	StartTime EndTime RestSeat TicketPage
	val items: ArrayList<ShowTimeReserveVO> = ArrayList(),
) {
	data class ShowTimeReserveVO(
		val startTime: Long = 0L,
		
		val endTime: Long = 0L,
		
		val restSeat: Int = 0,
		
		val ticketPage: String = "",
	)
}

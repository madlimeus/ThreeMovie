package com.threemovie.threemovieapi.Entity.VO

data class ShowTimeVO(
	val screemEN: String = "",
	
	val totalSeat: Int = 200,

//	StartTime EndTime RestSeat TicketPage
	val items: ArrayList<HashMap<String, String>> = ArrayList(),
)

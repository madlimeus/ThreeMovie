package com.threemovie.threemovieapi.Entity.DTO

data class ShowTimeResponse(
	val movieKR: String = "MT제작기",
	
	val movieTheater: String = "MT",
	
	val brchKR: String = "신대방",
	
	val brchEN: String? = null,
	
	val date: String = "1998-01-20",
	
	val totalSeat: Int = 120,
	
	val playKind: String = "0120",
	
	val screenKR: String = "0120",
	
	val screenEN: String? = null,
	
	val addrKR: String = "신대방동",
	
	val addrEN: String? = null,
	
	val items: List<ShowTimeItems>? = null,
)

data class ShowTimeItems(
	val ticketPage: String,
	val startTime: String,
	val endTime: String,
	val restSeat: String
)

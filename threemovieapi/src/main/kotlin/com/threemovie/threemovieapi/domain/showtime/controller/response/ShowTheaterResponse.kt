package com.threemovie.threemovieapi.domain.showtime.controller.response

data class ShowTheaterResponse(
	val city: String = "신대방",
	
	val items: List<ShowTheaterItems>? = null,
) {
	data class ShowTheaterItems(
		val movieTheater: String = "MT",
		
		val brchKR: String = "본점",
		
		val brchEN: String? = null,
		
		val addrKR: String = "삼거리",
		
		val addrEN: String? = "null",
	)
}

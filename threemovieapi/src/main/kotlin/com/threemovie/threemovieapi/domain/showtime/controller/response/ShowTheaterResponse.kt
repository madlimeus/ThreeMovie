package com.threemovie.threemovieapi.domain.showtime.controller.response

data class ShowTheaterResponse(
	val city: String = "신대방",
	
	val items: List<ShowTheaterItems>? = null,
) {
	data class ShowTheaterItems(
		val movieTheater: String = "MT",
		
		val brchKr: String = "본점",
		
		val brchEn: String? = null,
		
		val addrKr: String = "삼거리",
		
		val addrEn: String? = "null",
	)
}

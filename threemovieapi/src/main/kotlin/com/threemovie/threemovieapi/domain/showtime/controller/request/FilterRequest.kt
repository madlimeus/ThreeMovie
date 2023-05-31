package com.threemovie.threemovieapi.domain.showtime.controller.request

data class FilterRequest(
	val movieFilter: List<String>? = null,
	val movieTheaterFilter: List<String>? = null,
	val brchFilter: List<String>? = null,
	val dateFilter: List<Int>? = null,
)

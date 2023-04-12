package com.threemovie.threemovieapi.Entity.DTO.Request

data class FilterRequest(
	val movieFilter: List<String>? = null,
	val movieTheaterFilter: List<String>? = null,
	val brchFilter: List<String>? = null,
	val dateFilter: List<String>? = null,
)

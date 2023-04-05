package com.threemovie.threemovieapi.Entity.DTO

data class filterRequest(
	val movieFilter: List<String>? = null,
	val movieTheaterFilter: List<String>? = null,
	val theaterFilter: List<String>? = null,
	val dateFilter: List<String>? = null,
)

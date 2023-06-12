package com.threemovie.threemovieapi.domain.showtime.entity.dto

data class ShowTimeBranchDTO(
	val movieTheater: String = "MT",
	
	var city: String = "신대방",
	
	var brchKr: String = "삼거리",
	
	var brchEn: String = "sin",
	
	var date: Long = 202304050703,
)

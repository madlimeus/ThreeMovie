package com.threemovie.threemovieapi.domain.theater.entity.dto

import com.querydsl.core.annotations.QueryProjection

data class TheaterCityDTO @QueryProjection constructor(
	val city: String,
	val movieTheater: String,
	val branches: Set<BranchDTO>
)

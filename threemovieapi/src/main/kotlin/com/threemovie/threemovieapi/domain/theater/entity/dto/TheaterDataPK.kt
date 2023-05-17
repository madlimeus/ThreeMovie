package com.threemovie.threemovieapi.domain.theater.entity.dto

import java.io.Serializable

data class TheaterDataPK(
	val movieTheater: String = "MT",
	val theaterCode: String = "1234",
) : Serializable

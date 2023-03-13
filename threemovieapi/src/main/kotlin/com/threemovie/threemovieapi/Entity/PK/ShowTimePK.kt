package com.threemovie.threemovieapi.Entity.PK

import java.io.Serializable

data class ShowTimePK(
	val movieId: String = "아바타_2012",
	val brchKR: String = "마곡",
	val screenKR: String = "1관",
	val movieTheater: String = "MT",
	val date: String = "2023-03-12",
	val startTime: String = "10:30",
) : Serializable

package com.threemovie.threemovieapi.Entity.PK

import java.io.Serializable

data class ShowTimePK(
	val brchKR: String = "마곡",
	val movieKR: String = "아바타",
	val screenKR: String = "1관",
	val movieTheater: String = "MT",
	val date: String = "2023-03-12",
	val playKind: String = "2D",
) : Serializable

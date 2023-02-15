package com.threemovie.threemovieapi.service

import com.threemovie.threemovieapi.Entity.ShowTime

interface ShowTimeService {
	fun getShowTime(MovieTheater: String): List<ShowTime>

	fun getShowTimeAll(): List<ShowTime>
}

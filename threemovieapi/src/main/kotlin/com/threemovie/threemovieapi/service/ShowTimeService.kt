package com.threemovie.threemovieapi.service

import com.threemovie.threemovieapi.Entity.ShowTime

interface ShowTimeService {
	fun getShowTime(movieTheater: String): List<ShowTime>

	fun getShowTimeAll(): List<ShowTime>
}

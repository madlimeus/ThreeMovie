package com.threemovie.threemovieapi.service

import com.threemovie.threemovieapi.Entity.TheaterData

interface TheaterDataService {
	fun getTheaterData(movieTheater: String): List<TheaterData>

	fun getTheaterDataAll(): List<TheaterData>
}

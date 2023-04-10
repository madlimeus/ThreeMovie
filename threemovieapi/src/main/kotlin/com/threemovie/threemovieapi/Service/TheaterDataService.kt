package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.TheaterData

interface TheaterDataService {
	fun getTheaterData(movieTheater: String): List<TheaterData>

	fun getTheaterDataAll(): List<TheaterData>
}

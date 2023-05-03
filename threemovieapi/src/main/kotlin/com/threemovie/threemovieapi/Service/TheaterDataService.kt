package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.TheaterData
import com.threemovie.threemovieapi.Repository.Support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.Repository.UpdateTimeRepository
import org.springframework.stereotype.Service

@Service
class TheaterDataService(
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
	val updateTimeRepository: UpdateTimeRepository,
) {
	fun getTheaterDataAll(): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterDataAll()
	}
	
	fun getTheaterData(movieTheater: String): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterData(movieTheater)
	}
}

package com.threemovie.threemovieapi.domain.theater.service

import com.threemovie.threemovieapi.domain.theater.repository.support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.global.repository.UpdateTimeRepository
import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import com.threemovie.threemovieapi.global.exception.exception.DataNullException
import org.springframework.stereotype.Service

@Service
class TheaterDataService(
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
	val updateTimeRepository: UpdateTimeRepository,
) {
	fun getTheaterDataAll(): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterDataAll() ?: throw DataNullException()
	}
	
	fun getTheaterData(movieTheater: String): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterData(movieTheater)
	}
}

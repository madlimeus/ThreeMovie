package com.threemovie.threemovieapi.domain.theater.service

import com.threemovie.threemovieapi.domain.theater.entity.domain.Theater
import com.threemovie.threemovieapi.domain.theater.repository.support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.global.exception.exception.DataNullException
import com.threemovie.threemovieapi.global.repository.LastUpdateTimeRepository
import org.springframework.stereotype.Service

@Service
class TheaterDataService(
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
	val updateTimeRepository: LastUpdateTimeRepository,
) {
	fun getTheaterDataAll(): List<Theater> {
		return theaterDataRepositorySupport.getTheaterDataAll() ?: throw DataNullException()
	}
	
	fun getTheaterData(movieTheater: String): List<Theater> {
		return theaterDataRepositorySupport.getTheaterData(movieTheater)
	}
}

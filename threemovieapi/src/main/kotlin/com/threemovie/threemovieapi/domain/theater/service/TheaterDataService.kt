package com.threemovie.threemovieapi.domain.theater.service

import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import com.threemovie.threemovieapi.domain.theater.entity.dto.TheaterDTO
import com.threemovie.threemovieapi.domain.theater.repository.support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.global.exception.exception.DataNullException
import org.springframework.stereotype.Service

@Service
class TheaterDataService(
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
) {
	fun getTheaterDataAll(): List<TheaterDTO> {
		return theaterDataRepositorySupport.getTheaterAll() ?: throw DataNullException()
	}
	
	fun getTheaterData(movieTheater: String): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterEntityByMT(movieTheater)
	}
}

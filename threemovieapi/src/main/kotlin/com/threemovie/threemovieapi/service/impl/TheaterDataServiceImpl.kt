package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.TheaterData
import com.threemovie.threemovieapi.Repository.Support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.Repository.UpdateTimeRepository
import com.threemovie.threemovieapi.service.TheaterDataService
import org.springframework.stereotype.Service

@Service
class TheaterDataServiceImpl(
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
	val updateTimeRepository: UpdateTimeRepository,
) : TheaterDataService {
	override fun getTheaterDataAll(): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterDataAll()
	}

	override fun getTheaterData(movieTheater: String): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterData(movieTheater)
	}
}

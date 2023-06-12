package com.threemovie.threemovieapi.domain.theater.service

import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import com.threemovie.threemovieapi.domain.theater.entity.dto.BranchDTO
import com.threemovie.threemovieapi.domain.theater.entity.dto.TheaterCityDTO
import com.threemovie.threemovieapi.domain.theater.repository.support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.global.exception.exception.DataNullException
import org.springframework.stereotype.Service

@Service
class TheaterDataService(
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
) {
	fun getTheaterDataAll(): List<TheaterCityDTO> {
		val theaters = theaterDataRepositorySupport.getTheaterAll()
		
		
		if (theaters.isNullOrEmpty())
			throw DataNullException()
		
		val theaterMap = HashMap<Pair<String, String>, ArrayList<BranchDTO>>()
		
		theaters.forEach { theater ->
			val key = Pair(theater.movieTheater, theater.city)
			if (theaterMap[key] == null) {
				theaterMap[key] = ArrayList()
			}
			
			theaterMap[key]?.add(
				BranchDTO(theater.brchKr, theater.brchEn, theater.addrKr, theater.addrEn)
			)
		}
		
		val ret = ArrayList<TheaterCityDTO>()
		
		theaterMap.forEach { (key, value) ->
			ret.add(TheaterCityDTO(key.second, key.first, value.toSet()))
		}
		
		return ret.sortedByDescending { it.branches.size }
	}
	
	fun getTheaterData(movieTheater: String): List<TheaterData> {
		return theaterDataRepositorySupport.getTheaterEntityByMT(movieTheater)
	}
}

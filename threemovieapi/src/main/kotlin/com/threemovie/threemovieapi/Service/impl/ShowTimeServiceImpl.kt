package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Entity.DTO.ShowMovieDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTheaterDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTimeItemDTO
import com.threemovie.threemovieapi.Entity.DTO.filterRequest
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Service.ShowTimeService
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val showTimeRepositorySupport: ShowTimeRepositorySupport,
) : ShowTimeService {
	override fun getMovieList(): List<ShowMovieDTO> {
		return showTimeRepositorySupport.getMovieList()
	}
	
	override fun getTheaterList(filter: filterRequest): List<ShowTheaterDTO> {
		return showTimeRepositorySupport.getTheaterList(
			filter.movieFilter,
			filter.dateFilter
		)
	}
	
	override fun getDateList(filter: filterRequest): List<String> {
		val theaterFilter = makeTheaterList(
			filter.movieTheaterFilter,
			filter.theaterFilter
		)
		return showTimeRepositorySupport.getDateList(filter.movieFilter, theaterFilter)
	}
	
	override fun getShowTimeList(filter: filterRequest): List<ShowTimeItemDTO> {
		val theaterFilter = makeTheaterList(
			filter.movieTheaterFilter,
			filter.theaterFilter
		)
		return showTimeRepositorySupport.getShowTimeList(
			filter.movieFilter,
			theaterFilter,
			filter.dateFilter
		)
	}
	
	fun makeTheaterList(movieTheater: List<String>?, brchKR: List<String>?): List<Pair<String, String>>? {
		var ret = ArrayList<Pair<String, String>>()
		
		if (movieTheater == null || brchKR == null)
			return null
		
		
		for (i in movieTheater.indices) {
			ret.add(Pair(movieTheater[i], brchKR[i]))
		}
		
		return ret
	}
}

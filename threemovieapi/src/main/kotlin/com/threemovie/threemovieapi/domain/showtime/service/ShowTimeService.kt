package com.threemovie.threemovieapi.domain.showtime.service

import com.threemovie.threemovieapi.domain.showtime.controller.request.FilterRequest
import com.threemovie.threemovieapi.domain.showtime.controller.response.ShowTheaterResponse
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowDateDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowMovieDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimeItemDTO
import com.threemovie.threemovieapi.domain.showtime.repository.support.ShowTimeRepositorySupport
import org.springframework.stereotype.Service

@Service
class ShowTimeService(
	val showTimeRepositorySupport: ShowTimeRepositorySupport,
) {
	fun getMovieList(): List<ShowMovieDTO> {
		val ret = showTimeRepositorySupport.getMovieList()
		println(ret)
		return ret
	}
	
	fun getTheaterList(filter: FilterRequest?): List<ShowTheaterResponse> {
		val dtolist = showTimeRepositorySupport.getTheaterList(
			filter?.movieFilter,
			filter?.dateFilter
		)
		
		val cityMap = HashMap<String, ArrayList<ShowTheaterResponse.ShowTheaterItems>>()
		
		for (item in dtolist) {
			val tmpItem = ShowTheaterResponse.ShowTheaterItems(
				item.movieTheater,
				item.brchKr,
				item.brchEn,
				item.addrKr,
				item.addrEn
			)
			if (cityMap[item.city] == null) {
				cityMap[item.city] = ArrayList()
			}
			cityMap[item.city]?.add(tmpItem)
			
		}
		
		val ret = ArrayList<ShowTheaterResponse>()
		for (item in cityMap) {
			val tmpRes = ShowTheaterResponse(item.key, item.value)
			ret.add(tmpRes)
		}
		
		return ret.sortedByDescending { it.items?.size }
	}
	
	fun getDateList(filter: FilterRequest): List<ShowDateDTO> {
		val theaterFilter = makeTheaterList(
			filter.movieTheaterFilter,
			filter.brchFilter
		)
		val ret = showTimeRepositorySupport.getDateList(filter.movieFilter, theaterFilter)
		
		return ret
	}
	
	fun getShowTimeList(filter: FilterRequest?): List<ShowTimeItemDTO> {
		if (filter?.brchFilter.isNullOrEmpty() || filter?.dateFilter.isNullOrEmpty())
			return ArrayList()
		
		val theaterFilter = makeTheaterList(
			filter?.movieTheaterFilter,
			filter?.brchFilter
		)
		
		val dtolist = showTimeRepositorySupport.getShowTimeList(
			filter?.movieFilter,
			theaterFilter,
			filter?.dateFilter
		).sortedBy { it.movieKr }
		
		return dtolist
	}
	
	fun makeTheaterList(movieTheater: List<String>?, brchKR: List<String>?): List<Pair<String, String>>? {
		var ret = ArrayList<Pair<String, String>>()
		
		if (movieTheater.isNullOrEmpty() || brchKR.isNullOrEmpty())
			return null
		
		
		for (i in movieTheater.indices) {
			ret.add(Pair(movieTheater[i], brchKR[i]))
		}
		
		return ret
	}
}

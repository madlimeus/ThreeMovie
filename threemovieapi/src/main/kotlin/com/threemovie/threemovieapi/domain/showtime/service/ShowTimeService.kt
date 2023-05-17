package com.threemovie.threemovieapi.domain.showtime.service

import com.threemovie.threemovieapi.domain.showtime.controller.request.FilterRequest
import com.threemovie.threemovieapi.domain.showtime.controller.response.ShowTheaterResponse
import com.threemovie.threemovieapi.domain.showtime.controller.response.ShowTimeResponse
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowDateDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowMovieDTO
import com.threemovie.threemovieapi.domain.showtime.repository.support.ShowTimeRepositorySupport
import org.json.JSONArray
import org.springframework.stereotype.Service

@Service
class ShowTimeService(
	val showTimeRepositorySupport: ShowTimeRepositorySupport,
) {
	fun getMovieList(): List<ShowMovieDTO> {
		return showTimeRepositorySupport.getMovieList()
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
				item.brchKR,
				item.brchEN,
				item.addrKR,
				item.addrEN
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
		
		val comparator: Comparator<ShowTheaterResponse> = compareBy { it.items?.size }
		
		return ret.sortedWith(comparator).reversed()
	}
	
	fun getDateList(filter: FilterRequest): List<ShowDateDTO> {
		val theaterFilter = makeTheaterList(
			filter.movieTheaterFilter,
			filter.brchFilter
		)
		return showTimeRepositorySupport.getDateList(filter.movieFilter, theaterFilter)
	}
	
	fun getShowTimeList(filter: FilterRequest?): List<ShowTimeResponse> {
		if (filter?.movieFilter.isNullOrEmpty() || filter?.brchFilter.isNullOrEmpty() || filter?.dateFilter.isNullOrEmpty())
			return ArrayList<ShowTimeResponse>()
		
		val theaterFilter = makeTheaterList(
			filter?.movieTheaterFilter,
			filter?.brchFilter
		)
		
		val dtolist = showTimeRepositorySupport.getShowTimeList(
			filter?.movieFilter,
			theaterFilter,
			filter?.dateFilter
		)
		val ret = ArrayList<ShowTimeResponse>()
		
		for (showtime in dtolist) {
			val items = ArrayList<ShowTimeResponse.ShowTimeItems>()
			val jsonArr = JSONArray(showtime.items)
			
			for (i in 0 until jsonArr.length()) {
				val item = jsonArr.getJSONObject(i)
				val tmpItem = ShowTimeResponse.ShowTimeItems(
					item["TicketPage"].toString(),
					item["StartTime"].toString(),
					item["EndTime"].toString(),
					item["RestSeat"].toString()
				)
				items.add(tmpItem)
			}
			
			val tmpResponse = ShowTimeResponse(
				showtime.movieKR,
				showtime.movieTheater,
				showtime.brchKR,
				showtime.brchEN,
				showtime.date,
				showtime.totalSeat,
				showtime.playKind,
				showtime.screenKR,
				showtime.screenEN,
				showtime.addrKR,
				showtime.addrEN,
				items
			)
			
			ret.add(
				tmpResponse
			)
		}
		
		return ret
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

package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Entity.DTO.*
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Service.ShowTimeService
import org.json.JSONArray
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val showTimeRepositorySupport: ShowTimeRepositorySupport,
) : ShowTimeService {
	override fun getMovieList(): List<ShowMovieDTO> {
		return showTimeRepositorySupport.getMovieList()
	}
	
	override fun getTheaterList(filter: FilterRequest?): List<ShowTheaterResponse> {
		val dtolist = showTimeRepositorySupport.getTheaterList(
			filter?.movieFilter,
			filter?.dateFilter
		)
		
		val cityMap = HashMap<String, ArrayList<ShowTheaterItems>>()
		
		for (item in dtolist) {
			val tmpItem = ShowTheaterItems(item.movieTheater, item.brchKR, item.brchEN, item.addrKR, item.addrEN)
			if (cityMap[item.city] == null) {
				cityMap[item.city] = ArrayList<ShowTheaterItems>()
			}
			cityMap[item.city]?.add(tmpItem)
			
		}
		
		val ret = ArrayList<ShowTheaterResponse>()
		for (item in cityMap) {
			val tmpRes = ShowTheaterResponse(item.key, item.value)
			ret.add(tmpRes)
		}
		
		val comparator: Comparator<ShowTheaterResponse> = compareBy { it.items?.size }
		val orderRet = ret.sortedWith(comparator).reversed()
		
		return orderRet
	}
	
	override fun getDateList(filter: FilterRequest?): List<ShowDateDTO> {
		val theaterFilter = makeTheaterList(
			filter?.movieTheaterFilter,
			filter?.brchFilter
		)
		return showTimeRepositorySupport.getDateList(filter?.movieFilter, theaterFilter)
	}
	
	override fun getShowTimeList(filter: FilterRequest?): List<ShowTimeResponse> {
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
			val items = ArrayList<ShowTimeItems>()
			val jsonArr = JSONArray(showtime.items)
			
			for (i in 0 until jsonArr.length()) {
				val item = jsonArr.getJSONObject(i)
				val tmpItem = ShowTimeItems(
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

package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.*

interface ShowTimeService {
	fun getMovieList(): List<ShowMovieDTO>
	
	fun getTheaterList(filter: FilterRequest?): List<ShowTheaterResponse>
	
	fun getDateList(filter: FilterRequest?): List<ShowDateDTO>
	
	fun getShowTimeList(filter: FilterRequest?): List<ShowTimeResponse>
}

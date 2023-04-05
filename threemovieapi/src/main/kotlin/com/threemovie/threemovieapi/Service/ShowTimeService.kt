package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.ShowMovieDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTheaterDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTimeItemDTO
import com.threemovie.threemovieapi.Entity.DTO.filterRequest

interface ShowTimeService {
	fun getMovieList(): List<ShowMovieDTO>
	
	fun getTheaterList(filter: filterRequest): List<ShowTheaterDTO>
	
	fun getDateList(filter: filterRequest): List<String>
	
	fun getShowTimeList(filter: filterRequest): List<ShowTimeItemDTO>
}

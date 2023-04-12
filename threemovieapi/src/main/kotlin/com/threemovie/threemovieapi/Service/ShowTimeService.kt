package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.Request.FilterRequest
import com.threemovie.threemovieapi.Entity.DTO.Response.ShowTheaterResponse
import com.threemovie.threemovieapi.Entity.DTO.Response.ShowTimeResponse
import com.threemovie.threemovieapi.Entity.DTO.ShowDateDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowMovieDTO

interface ShowTimeService {
	fun getMovieList(): List<ShowMovieDTO>
	
	fun getTheaterList(filter: FilterRequest?): List<ShowTheaterResponse>
	
	fun getDateList(filter: FilterRequest?): List<ShowDateDTO>
	
	fun getShowTimeList(filter: FilterRequest?): List<ShowTimeResponse>
}

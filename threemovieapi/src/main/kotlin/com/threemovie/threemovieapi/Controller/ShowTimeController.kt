package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Entity.DTO.Request.FilterRequest
import com.threemovie.threemovieapi.Entity.DTO.Response.ShowTheaterResponse
import com.threemovie.threemovieapi.Entity.DTO.Response.ShowTimeResponse
import com.threemovie.threemovieapi.Entity.DTO.ShowDateDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowMovieDTO
import com.threemovie.threemovieapi.Service.ShowTimeService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ShowTimeController(val showTimeService: ShowTimeService) {
	@QueryMapping
	fun getMovieList(): List<ShowMovieDTO> = showTimeService.getMovieList()
	
	@QueryMapping
	fun getTheaterList(@Argument filter: FilterRequest): List<ShowTheaterResponse> =
		showTimeService.getTheaterList(filter)
	
	@QueryMapping
	fun getDateList(@Argument filter: FilterRequest): List<ShowDateDTO> = showTimeService.getDateList(filter)
	
	@QueryMapping
	fun getShowTimeList(@Argument filter: FilterRequest): List<ShowTimeResponse> =
		showTimeService.getShowTimeList(filter)
}

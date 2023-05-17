package com.threemovie.threemovieapi.domain.showtime.controller

import com.threemovie.threemovieapi.domain.showtime.controller.request.FilterRequest
import com.threemovie.threemovieapi.domain.showtime.controller.response.ShowTheaterResponse
import com.threemovie.threemovieapi.domain.showtime.controller.response.ShowTimeResponse
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowDateDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowMovieDTO
import com.threemovie.threemovieapi.domain.showtime.service.ShowTimeService
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

package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.DTO.ShowMovieDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTheaterDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTimeItemDTO
import com.threemovie.threemovieapi.Entity.DTO.filterRequest
import com.threemovie.threemovieapi.Service.impl.ShowTimeServiceImpl
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ShowTimeController(val service: ShowTimeServiceImpl) {
	@QueryMapping
	fun getMovieList(): List<ShowMovieDTO> = service.getMovieList()
	
	@QueryMapping
	fun getTheaterList(@Argument filter: filterRequest): List<ShowTheaterDTO> = service.getTheaterList(filter)
	
	@QueryMapping
	fun getDateList(@Argument filter: filterRequest): List<String> = service.getDateList(filter)
	
	@QueryMapping
	fun getShowTimeList(@Argument filter: filterRequest): List<ShowTimeItemDTO> = service.getShowTimeList(filter)
}

package com.threemovie.threemovieapi.domain.theater.controller

import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import com.threemovie.threemovieapi.domain.theater.service.TheaterDataService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/theater")
class TheaterDataController(val service: TheaterDataService) {
	@GetMapping("/{MovieTheater}")
	fun getTheaterData(@PathVariable MovieTheater: String): List<TheaterData> = service.getTheaterData(MovieTheater)
	
	@GetMapping
	fun getTheaterDataAll(): List<TheaterData> = service.getTheaterDataAll()
}

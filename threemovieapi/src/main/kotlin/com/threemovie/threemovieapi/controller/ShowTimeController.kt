package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.service.impl.ShowTimeServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movie")
class ShowTimeController(val service: ShowTimeServiceImpl) {

	@GetMapping("/CGV")
	fun getCGV(): List<ShowTime> = service.getCGV()

}

package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Service.MovieDataService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movie")
class MovieDataController(
	val movieDataService: MovieDataService
) {
	@GetMapping("/movielist")
	fun getMovieList(): List<MovieListDTO> {
		return movieDataService.getMovieList()
	}
	
	@GetMapping("/detail/{movieId}")
	fun getMovieDetail(@PathVariable movieId: String): MovieDetailDTO {
		return movieDataService.getMovieDetail(movieId)
	}
	
}

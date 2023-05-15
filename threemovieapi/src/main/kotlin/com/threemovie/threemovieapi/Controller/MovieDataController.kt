package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Service.MovieDataService
import org.springframework.http.ResponseEntity
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
	fun getMovieList(): ResponseEntity<List<MovieListDTO>> {
		return ResponseEntity.ok(movieDataService.getMovieList())
	}
	
	@GetMapping("/detail/{movieId}")
	fun getMovieDetail(@PathVariable movieId: String): ResponseEntity<MovieDetailDTO> {
		return ResponseEntity.ok(movieDataService.getMovieDetail(movieId))
	}
	
}

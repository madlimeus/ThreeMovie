package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Service.MovieDataService
import org.springframework.http.HttpStatus
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
		val ret = movieDataService.getMovieList()
		if (ret.isNullOrEmpty())
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
		return ResponseEntity.ok(ret)
	}
	
	@GetMapping("/detail/{movieId}")
	fun getMovieDetail(@PathVariable movieId: String): ResponseEntity<MovieDetailDTO> {
		val ret = movieDataService.getMovieDetail(movieId) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
		return ResponseEntity.ok(ret)
	}
	
}

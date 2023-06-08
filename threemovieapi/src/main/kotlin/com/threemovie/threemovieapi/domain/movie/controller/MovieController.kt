package com.threemovie.threemovieapi.domain.movie.controller

import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieDetailDTO
import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieListDTO
import com.threemovie.threemovieapi.domain.movie.service.MovieDataControlService
import com.threemovie.threemovieapi.domain.movie.service.MovieDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movie")
class MovieController(
	val movieService: MovieDataService,
	val movieDataControlService: MovieDataControlService
) {
	@GetMapping("/main")
	fun getMovieList(): ResponseEntity<List<MovieListDTO>> {
		val ret = movieService.getMovieList()
		println(ret)
		return ResponseEntity.ok(ret)
	}
	
	@GetMapping("/detail/{movieId}")
	fun getMovieDetail(@PathVariable movieId: String): ResponseEntity<MovieDetailDTO> {
		return ResponseEntity.ok(movieService.getMovieDetail(movieId))
	}
}

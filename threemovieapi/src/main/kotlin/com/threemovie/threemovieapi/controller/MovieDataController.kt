package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Service.impl.MovieDataControlServiceimpl
import com.threemovie.threemovieapi.Service.impl.MovieInfoServiceimpl
import com.threemovie.threemovieapi.Utils.ChkNeedUpdate
import com.threemovie.threemovieapi.Utils.Review.GetReviewFromTheater
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/movieinfo")
class MovieDataController(
	val movieInfoService: MovieInfoServiceimpl,
	val movieDataControlService: MovieDataControlServiceimpl,
	val UpdateTimeRepositorySupport: UpdateTimeRepositorySupport
) {

	@GetMapping()
	fun getMovieDataFromDaum() {
		movieDataControlService.truncateAllMovieData()
		movieDataControlService.GET_MOVIE_INFO_DAUM()
		movieDataControlService.GET_MOVIE_INFO_DAUM_for_upcoming()
	}

	@GetMapping("/movielist")
	fun getMovieList(): List<MovieListDTO> {
		return movieInfoService.getMovieList()
	}

	@GetMapping("/moviedetail/{movieId}")
	fun getMovieDetail(@PathVariable movieId: String): MovieDetailDTO {
		return movieInfoService.getMovieDetail(movieId)
	}

}

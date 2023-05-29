package com.threemovie.threemovieapi.domain.movie.controller

import com.threemovie.threemovieapi.domain.movie.repository.MovieReviewRepository
import com.threemovie.threemovieapi.domain.movie.service.MovieReviewService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/review")
class ReviewController(
	val MovieReviewService : MovieReviewService,
	val movieReviewRepository: MovieReviewRepository,
) {
	@GetMapping("/test")
	fun getreview() {
		movieReviewRepository.truncate()
        MovieReviewService.saveReviewData()
	}
}

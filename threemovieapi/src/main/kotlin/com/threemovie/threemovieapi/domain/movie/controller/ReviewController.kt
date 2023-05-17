package com.threemovie.threemovieapi.domain.movie.controller

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//@RequestMapping("/api/review")
class ReviewController {
	//    @GetMapping()
	@QueryMapping
	fun getreview() {
//        MovieReviewService.turncate_MovieReview()
//        MovieReviewService.save_MovieReview()
	}
}

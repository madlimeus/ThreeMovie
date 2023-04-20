package com.threemovie.threemovieapi.Controller

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.web.bind.annotation.RestController

@RestController
//@RequestMapping("/api/review")
class ReviewDataController {
	//    @GetMapping()
	@QueryMapping
	fun getreview() {
//        MovieReviewService.turncate_MovieReview()
//        MovieReviewService.save_MovieReview()
	}
}

package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Utils.ChkNeedUpdate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.graphql.data.method.annotation.QueryMapping

@RestController
//@RequestMapping("/api/review")
class ReviewDataController(
//    val MovieReviewService: MovieReviewService,
) {
//    @GetMapping()
    @QueryMapping
    fun getreview() {
//        MovieReviewService.turncate_MovieReview()
//        MovieReviewService.save_MovieReview()
    }
}
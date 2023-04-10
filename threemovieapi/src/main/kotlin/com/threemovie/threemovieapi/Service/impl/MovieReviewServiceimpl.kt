package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.MovieReview
import com.threemovie.threemovieapi.Repository.MovieReviewRepository
import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.MovieReviewService
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class MovieReviewServiceimpl(
    val MovieReviewRepository: MovieReviewRepository
): MovieReviewService {
    override fun turncate_MovieReview() {
        MovieReviewRepository.truncate()
    }

}
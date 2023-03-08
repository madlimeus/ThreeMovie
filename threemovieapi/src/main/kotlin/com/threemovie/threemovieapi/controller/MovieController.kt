package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.impl.MovieDataServiceimpl
import com.threemovie.threemovieapi.service.impl.MovieCreatorServiceimpl
import com.threemovie.threemovieapi.service.impl.MoviePreviewServiceimpl
import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/movieinfo")
class MovieController(
    val MovieDataService: MovieDataServiceimpl,
    val MovieCreatorService: MovieCreatorServiceimpl,
    val MoviePreviewService: MoviePreviewServiceimpl
) {

    @GetMapping()
    fun GET_MOVIE_INFO_DAUM(){
        val url_Daum_Main = "https://movie.daum.net/"
        val api_list_screening = "api/premovie?page=1&size=100"

        var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_list_screening);
        val list_screening_Array = JSONObject(tmp_data).getJSONArray("contents")

        try{
            MovieDataService.turncate_MovieInfo()
            MovieCreatorService.turncate_MovieCreator()
            MoviePreviewService.turncate_MoviePreview()
        } catch (e: Exception){
            println("turncate_error")
            println(e)
        }

        for (One_movie_Info in list_screening_Array) {
            val tmp_one_movie_data = JSONObject(One_movie_Info.toString())
            try{
                MovieDataService.save_MovieData(tmp_one_movie_data, url_Daum_Main)
                MovieCreatorService.save_MovieCreator(tmp_one_movie_data, url_Daum_Main)
                MoviePreviewService.save_MoviePreview(tmp_one_movie_data, url_Daum_Main)
            } catch (e: Exception) {
                println("save_error")
                println("movie name : "+tmp_one_movie_data.get("titleKorean"))
                println(e)
            }
        }
    }
}
package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.MovieDataControlService
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieDataControlServiceimpl(
    val movieInfoService: MovieInfoServiceimpl,
    val movieCreatorService: MovieCreatorServiceimpl,
    val moviePreviewService: MoviePreviewServiceimpl
) : MovieDataControlService {
    override fun GET_MOVIE_INFO_DAUM() {
        val url_Daum_Main = "https://movie.daum.net/"
        val api_list_screening = "api/premovie?page=1&size=100"

        var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_list_screening);
        val list_screening_Array = JSONObject(tmp_data).getJSONArray("contents")

        for (One_movie_Info in list_screening_Array) {
            val tmp_one_movie_data = JSONObject(One_movie_Info.toString())
            try {
                movieInfoService.save_MovieData(tmp_one_movie_data, url_Daum_Main)
                movieCreatorService.save_MovieCreator(tmp_one_movie_data, url_Daum_Main)
                moviePreviewService.save_MoviePreview(tmp_one_movie_data, url_Daum_Main)
            } catch (e: Exception) {
                println("save_error")
                println("movie name : " + tmp_one_movie_data.get("titleKorean"))
                println(e)
            }
        }
    }

    override fun truncateAllMovieData(){
        try {
            movieInfoService.turncate_MovieData()
            movieCreatorService.turncate_MovieCreator()
            moviePreviewService.turncate_MoviePreview()
        } catch (e: Exception) {
            println("truncate error")
            println(e)
        }
    }
}
package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieDataControlService(
	val movieDataService: MovieDataService,
	val movieCreatorService: MovieCreatorService,
	val moviePreviewService: MoviePreviewService
) {
	fun GET_MOVIE_DATA_DAUM() {
		val url_Daum_Main = "https://movie.daum.net/"
		val api_list_screening = "api/premovie?page=1&size=100"
		
		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_list_screening)
		val list_screening_Array = JSONObject(tmp_data).getJSONArray("contents")
		
		for (One_movie_data in list_screening_Array) {
			val tmp_one_movie_data = JSONObject(One_movie_data.toString())
			try {
				movieDataService.save_MovieData(tmp_one_movie_data, url_Daum_Main)
				movieCreatorService.save_MovieCreator(tmp_one_movie_data, url_Daum_Main)
				moviePreviewService.save_MoviePreview(tmp_one_movie_data, url_Daum_Main)
			} catch (e: Exception) {
				println("save_error")
				println("movie name : " + tmp_one_movie_data.get("titleKorean"))
				println(e)
			}
		}
	}
	
	fun GET_MOVIE_DATA_DAUM_for_upcoming() {
		val url_Daum_Main = "https://movie.daum.net/"
		val api_list_screening = "api/premovie?page=1&size=100&flag=C"
		
		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_list_screening)
		val list_screening_Array = JSONObject(tmp_data).getJSONArray("contents")
		
		for (One_movie_data in list_screening_Array) {
			val tmp_one_movie_data = JSONObject(One_movie_data.toString())
			try {
				movieDataService.save_MovieData(tmp_one_movie_data, url_Daum_Main)
				movieCreatorService.save_MovieCreator(tmp_one_movie_data, url_Daum_Main)
				moviePreviewService.save_MoviePreview(tmp_one_movie_data, url_Daum_Main)
			} catch (e: Exception) {
				println("save_error")
				println("movie name : " + tmp_one_movie_data.get("titleKorean"))
				println(e)
			}
		}
	}
	
	fun truncateAllMovieData() {
		try {
			movieDataService.turncate_MovieData()
			movieCreatorService.turncate_MovieCreator()
			moviePreviewService.turncate_MoviePreview()
		} catch (e: Exception) {
			println("truncate error")
			println(e)
		}
	}
}

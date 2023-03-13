package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.impl.MovieCreatorServiceimpl
import com.threemovie.threemovieapi.service.impl.MovieInfoServiceimpl
import com.threemovie.threemovieapi.service.impl.MoviePreviewServiceimpl
import org.json.JSONObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/movieinfo")
class MovieInfoController(
	val movieInfoService: MovieInfoServiceimpl,
	val movieCreatorService: MovieCreatorServiceimpl,
	val moviePreviewService: MoviePreviewServiceimpl
) {

	@GetMapping()
	fun GET_MOVIE_INFO_DAUM() {
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

	@GetMapping("/test")
	fun turncate_test() {
		movieInfoService.turncate_MovieData()
	}

	@GetMapping("/movielist")
	fun getMovieList(): List<MovieListDTO> {
		return movieInfoService.getMovieList()
	}

	@GetMapping("/detail/{movieId}")
	fun getMovieDetail(@PathVariable movieId: String): MovieDetailDTO {
		return movieInfoService.getMovieDetail(movieId)
	}
}

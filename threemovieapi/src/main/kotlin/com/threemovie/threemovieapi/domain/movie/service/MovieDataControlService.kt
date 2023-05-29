package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieCreator
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.MoviePreview
import com.threemovie.threemovieapi.domain.movie.repository.MovieCreatorRepository
import com.threemovie.threemovieapi.domain.movie.repository.MovieDataRepository
import com.threemovie.threemovieapi.domain.movie.repository.MoviePreviewRepository
import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieDataControlService(
	val movieDataService: MovieDataService,
	val movieDataRepository: MovieDataRepository,
	val movieCreatorService: MovieCreatorService,
	val movieCreatorRepository: MovieCreatorRepository,
	val moviePreviewService: MoviePreviewService,
	val moviePreviewRepository: MoviePreviewRepository
) {
	
	fun GET_MOVIE_DATA_DAUM_for_upcoming() {
		val url_Daum_Main = "https://movie.daum.net/"
		val api_list_screening = "api/premovie?page=1&size=100&flag=Y"

		val movieData_List = ArrayList<MovieData>()
		val movieCreator_List = ArrayList<MovieCreator>()
		val moviePreview_List = ArrayList<MoviePreview>()

		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_list_screening)
		val list_screening_Array = JSONObject(tmp_data).getJSONArray("contents")
		
		for (One_movie_data in list_screening_Array) {
			val tmp_one_movie_data = JSONObject(One_movie_data.toString())
			try {
				movieData_List.add(
					movieDataService.save_MovieData(tmp_one_movie_data, url_Daum_Main)
				)
				movieCreator_List.add(
					movieCreatorService.save_MovieCreator(tmp_one_movie_data, url_Daum_Main)
				)
				moviePreview_List.add(
					moviePreviewService.save_MoviePreview(tmp_one_movie_data, url_Daum_Main)
				)
			} catch (e: Exception) {
				println("save_error")
				println("movie name : " + tmp_one_movie_data.get("titleKorean") + tmp_one_movie_data.get("id"))
				println(e)
			}
		}
		movieDataRepository.saveAll(movieData_List)
		movieCreatorRepository.saveAll(movieCreator_List)
		moviePreviewRepository.saveAll(moviePreview_List)
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

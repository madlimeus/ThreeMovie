package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.repository.MovieDataRepository
import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieDataControlService(
	val movieDataRepository: MovieDataRepository,
	val movieDataService: MovieDataService,
	val movieCreatorService: MovieCreatorService,
	val moviePreviewService: MoviePreviewService,
) {
	
	fun GET_MOVIE_DATA_DAUM_for_upcoming() {
		val url_Daum_Main = "https://movie.daum.net/"
		val api_list_screening = "api/premovie?page=1&size=100&flag=Y"
		
		val movieData_List = ArrayList<MovieData>()
		
		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_list_screening)
		val list_screening_Array = JSONObject(tmp_data).getJSONArray("contents")
		
		for (One_movie_data in list_screening_Array) {
			val tmp_one_movie_data = JSONObject(One_movie_data.toString())
			try {
				val movieId = tmp_one_movie_data.get("id").toString()
				val movieData = movieDataService.save_MovieData_new(movieId)
				val movieCreators = movieCreatorService.save_MovieCreator_new(movieId, movieData)
				val moviePreviews = moviePreviewService.save_MoviePreview_new(movieId, movieData)

				movieData.addCreators(movieCreators)
				movieData.addPreviews(moviePreviews)
				movieData_List.add(movieData)
			} catch (e: Exception) {
				println("save_error")
				println("movie name : " + tmp_one_movie_data.get("titleKorean") + tmp_one_movie_data.get("id"))
				println(e)
			}
		}
		movieDataRepository.saveAll(movieData_List)
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

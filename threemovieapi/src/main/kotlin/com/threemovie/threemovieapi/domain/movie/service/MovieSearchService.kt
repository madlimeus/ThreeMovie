package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.repository.MovieDataRepository
import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieSearchService(
	val movieDataRepository: MovieDataRepository,
	val movieDataService: MovieDataService,
	val movieCreatorService: MovieCreatorService,
	val moviePreviewService: MoviePreviewService
) {
	fun movieSearchService(movieName: String): String {
		val pattern = Regex("[^ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]")
		val result: String
		val url_search = "https://movie.daum.net/api/search/all?q=" + movieName.replace(pattern, "")
		val responseData = JSONObject(GET_DATA_USE_DAUM_API(url_search))
		val search_result = JSONObject(
			JSONObject(
				responseData
					.get("movie").toString()
			)
				.get("result").toString()
		)
			.get("search_result").toString()
		val documents = JSONObject(search_result).getJSONArray("documents")
		if (documents.length() > 0) {
			val resultMovieId = JSONObject(
				JSONObject(documents[0].toString())
					.get("document").toString()
			)
				.get("movieId").toString()
			saveMovieDataSearchSuccess(resultMovieId)
			result = resultMovieId
		} else {
			println("else")
			saveMovieDataSearchFail(movieName)
			result = movieName
		}
		return result
	}
	
	fun saveMovieDataSearchFail(movieName: String) {
		val movieData_List = ArrayList<MovieData>()
		try {
			val movieData = MovieData(
				movieName,
				null,
				null,
				null,
				movieName,
				null,
				0,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null
			)
			movieData_List.add(movieData)
		} catch (e: Exception) {
			println("error in searchFail")
			println(e)
		}
		movieDataRepository.saveAll(movieData_List)
	}
	
	fun saveMovieDataSearchSuccess(movieId: String) {
		val movieData_List = ArrayList<MovieData>()
		try {
			val movieData = movieDataService.save_MovieData_new(movieId)
			val movieCreators = movieCreatorService.save_MovieCreator_new(movieId, movieData)
			val moviePreviews = moviePreviewService.save_MoviePreview_new(movieId, movieData)
			
			movieData.addCreators(movieCreators)
			movieData.addPreviews(moviePreviews)
			movieData_List.add(movieData)
		} catch (e: Exception) {
			println("error in searchSuccess")
			println(e)
		}
		movieDataRepository.saveAll(movieData_List)
	}
}

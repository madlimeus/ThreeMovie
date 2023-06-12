package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieCreator
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.repository.MovieCreatorRepository
import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieCreatorService(
	val MovieCreatorRepository: MovieCreatorRepository
) {
	
	fun save_MovieCreator(One_movie_data: JSONObject, url_Daum_Main: String, movieData: MovieData): List<MovieCreator> {
		val api_movie_data_screening = "api/movie/" + One_movie_data.get("id").toString() + "/main"
		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_movie_data_screening)
		
		val movie_releaseDate =
			JSONObject(One_movie_data.get("countryMovieInformation").toString()).get("releaseDate").toString()
		
		val movie_data_array = JSONObject(tmp_data).getJSONArray("casts")
		
		var creators = ArrayList<MovieCreator>()
		
		for (One_person in movie_data_array) {
			val One_person_json = JSONObject(One_person.toString())
			
			var nameKR: String = One_person_json.getString("nameKorean")
			var nameEN: String? = One_person_json.get("nameEnglish").toString()
			var role = JSONObject(One_person_json.get("movieJob").toString()).get("role").toString()
			var roleKR: String? = One_person_json.get("description").toString()
			var link: String? = One_person_json.get("profileImage").toString()
			
			roleKR = if (roleKR.isNullOrEmpty()) role else roleKR
			
			
			creators.add(
				MovieCreator(
					nameKR,
					nameEN,
					roleKR,
					link,
					movieData
				)
			)
		}
		
		
		return creators
	}
	
	fun turncate_MovieCreator() {
		MovieCreatorRepository.truncate()
	}
	
}

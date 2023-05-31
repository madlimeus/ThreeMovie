package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieCreator
import com.threemovie.threemovieapi.domain.movie.repository.MovieCreatorRepository
import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieCreatorService(
	val MovieCreatorRepository: MovieCreatorRepository
) {
	
	fun save_MovieCreator(One_movie_data: JSONObject, url_Daum_Main: String): List<MovieCreator> {
		val api_movie_data_screening = "api/movie/" + One_movie_data.get("id").toString() + "/main"
		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_movie_data_screening)
		
		val movie_releaseDate =
			JSONObject(One_movie_data.get("countryMovieInformation").toString()).get("releaseDate").toString()
		
		val movie_data_array = JSONObject(tmp_data).getJSONArray("casts")
		
		var casts_items = JSONArray()
		var creators = ArrayList<MovieCreator>()
		
		for (One_person in movie_data_array) {
			val One_person_json = JSONObject(One_person.toString())
			var json_tmp = JSONObject()
			
			var nameKR: String? = One_person_json.get("nameKorean").toString()
			var nameEN: String? = One_person_json.get("nameEnglish").toString()
			var role = JSONObject(One_person_json.get("movieJob").toString()).get("role").toString()
			var roleKR: String? = One_person_json.get("description").toString()
			
			nameKR = if (nameKR != null && nameKR.length == 0) null else nameKR
			nameEN = if (nameEN != null && nameEN.length == 0) null else nameEN
			roleKR = if (roleKR != null && (roleKR.equals("null") || roleKR.length == 0)) role else roleKR
			
			creators.add(MovieCreator(nameKR as String, nameEN, roleKR, One_person_json.get("profileImage") as String))
		}
		
		
		return creators
	}
	
	fun turncate_MovieCreator() {
		MovieCreatorRepository.truncate()
	}
	
}

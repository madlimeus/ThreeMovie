package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Entity.MovieData
import com.threemovie.threemovieapi.Repository.MovieDataRepository
import com.threemovie.threemovieapi.Repository.Support.MovieDataRepositorySupport
import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieDataService(
	val movieDataRepository: MovieDataRepository,
	val movieDataRepositorySupport: MovieDataRepositorySupport,
) {
	fun getMovieDetail(movieId: String): MovieDetailDTO {
		return movieDataRepositorySupport.getMovieDetail(movieId)
	}
	
	fun getMovieList(): List<MovieListDTO> {
		return movieDataRepositorySupport.getMovieList()
	}
	
	fun getMoviedata(): List<MovieData> {
		return movieDataRepository.findAll()
	}
	
	fun save_MovieData(One_movie_data: JSONObject, url_Daum_Main: String) {
		val api_movie_data_screening = "api/movie/" + One_movie_data.get("id").toString() + "/main"
		
		var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_movie_data_screening)
		val movie_data_json = JSONObject(JSONObject(tmp_data).get("movieCommon").toString())
		val movie_releaseDate =
			JSONObject(One_movie_data.get("countryMoviedatarmation").toString()).get("releaseDate").toString()
		
		var netizenAvgRate: Double
		var reservationRate: Double
		if (! One_movie_data.get("netizenAvgRate").equals(null)) {
			netizenAvgRate = One_movie_data.get("netizenAvgRate").toString().toDouble()
		} else {
			netizenAvgRate = 0.0
		}
		try {
			reservationRate =
				JSONObject(One_movie_data.get("reservation").toString()).get("reservationRate").toString().toDouble()
		} catch (e: Exception) {
			reservationRate = 0.0
		}
		
		var Poster: String?
		try {
			Poster = JSONObject(movie_data_json.get("mainPhoto").toString()).get("imageUrl").toString()
		} catch (e: Exception) {
			Poster = null
		}
		
		var NameKR: String?
		if (movie_data_json.get("titleKorean").equals(null)) {
			NameKR = null
		} else {
			if (movie_data_json.get("titleKorean").toString().length == 0) {
				NameKR = null
			} else {
				NameKR = movie_data_json.get("titleKorean").toString()
			}
		}
		
		var NameEN: String?
		if (movie_data_json.get("titleEnglish").equals(null)) {
			NameEN = null
		} else {
			if (movie_data_json.get("titleEnglish").toString().length == 0) {
				NameEN = null
			} else {
				NameEN = movie_data_json.get("titleEnglish").toString()
			}
		}
		
		var plot: String? = movie_data_json.get("plot").toString()
		var makingNote: String? = movie_data_json.get("makingNote").toString()
//		var runningTime : String? = JSONObject(movie_data_json.get("countryMoviedatarmation").toString()).get("duration").toString()
//		var admissionCode : String? = JSONObject(movie_data_json.get("countryMoviedatarmation").toString()).get("admissionCode").toString()
		var reservationRank: String? = movie_data_json.get("reservationRank").toString()
		var totalAudience: String? = movie_data_json.get("totalAudienceCount").toString()
		var countryMoviedatarmation = movie_data_json.getJSONArray("countryMoviedatarmation")
		
		var runningTime: String? = null
		var admissionCode: String? = null
		for (arr_tmp in countryMoviedatarmation) {
			var countryId = JSONObject(JSONObject(arr_tmp.toString()).get("country").toString()).get("id").toString()
			if (countryId.equals("KR")) {
				runningTime = JSONObject(arr_tmp.toString()).get("duration").toString()
				admissionCode = JSONObject(arr_tmp.toString()).get("admissionCode").toString()
			}
		}
		
		plot = if (plot != null && (plot.equals("null") || plot.length == 0)) null else plot
		makingNote =
			if (makingNote != null && (makingNote.equals("null") || makingNote.length == 0)) null else makingNote
		runningTime =
			if (runningTime != null && (runningTime.equals("null") || runningTime.length == 0)) null else runningTime
		admissionCode =
			if (admissionCode != null && (admissionCode.equals("null") || admissionCode.length == 0)) null else admissionCode
		reservationRank =
			if (reservationRank != null && (reservationRank.equals("null") || reservationRank.length == 0)) null else reservationRank
		totalAudience = if (totalAudience != null && (totalAudience.equals("null"))) "0" else totalAudience
		
		val member_MovieData = MovieData(
			One_movie_data.get("titleKorean").toString() + "_" + movie_releaseDate,
			netizenAvgRate,
			reservationRate,
			plot,
			NameKR,
			NameEN,
			movie_releaseDate,
			Poster,
			movie_data_json.get("genres").toString().replace("\"", ""),
			makingNote,
			runningTime,
			admissionCode,
			movie_data_json.get("productionCountries").toString().replace("\"", ""),
			reservationRank,
			totalAudience
		)
		val res = movieDataRepository.save(member_MovieData)
	}
	
	fun turncate_MovieData() {
		movieDataRepository.truncate()
	}
}

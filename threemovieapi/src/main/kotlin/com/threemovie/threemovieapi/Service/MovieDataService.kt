package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Entity.MovieData
import org.json.JSONObject

interface MovieDataService {
	fun getMovieList(): List<MovieListDTO>
	
	fun getMovieDetail(movieId: String): MovieDetailDTO
	
	fun getMoviedata(): List<MovieData>
	
	fun save_MovieData(One_movie_data: JSONObject, url_Daum_Main: String)
	
	fun turncate_MovieData()
}

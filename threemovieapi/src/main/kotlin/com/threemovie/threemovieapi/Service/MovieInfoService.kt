package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Entity.MovieInfo
import org.json.JSONObject

interface MovieInfoService {
	fun getMovieList(): List<MovieListDTO>
	
	fun getMovieDetail(movieId: String): MovieDetailDTO
	
	fun getMovieInfo(): List<MovieInfo>
	
	fun save_MovieData(One_movie_Info: JSONObject, url_Daum_Main: String)
	
	fun turncate_MovieData()
}

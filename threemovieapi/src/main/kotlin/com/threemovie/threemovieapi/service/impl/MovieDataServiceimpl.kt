package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.MovieData
import com.threemovie.threemovieapi.Repository.MovieDataRepository
import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.MovieDataService
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class MovieDataServiceimpl(
    val MovieDataRepository: MovieDataRepository
) : MovieDataService {
    override fun save_MovieData(One_movie_Info: JSONObject, url_Daum_Main: String){
        val api_movie_data_screening = "api/movie/" + One_movie_Info.get("id").toString() + "/main"

        var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_movie_data_screening)
        val movie_data_json = JSONObject(JSONObject(tmp_data).get("movieCommon").toString())
        val movie_releaseDate = JSONObject(One_movie_Info.get("countryMovieInformation").toString()).get("releaseDate").toString()

        var Poster: String?
        try{
            Poster = JSONObject(movie_data_json.get("mainPhoto").toString()).get("imageUrl").toString()
        } catch (e: Exception){
            Poster = null
        }

        val member_MovieData = MovieData(
            One_movie_Info.get("titleKorean").toString()+"_"+movie_releaseDate,
            movie_data_json.get("plot").toString(),
            movie_data_json.get("titleKorean").toString(),
            movie_data_json.get("titleEnglish").toString(),
            movie_releaseDate,
            Poster,
            movie_data_json.get("genres").toString()
        )
        val res = MovieDataRepository.save(member_MovieData)
    }
    override fun turncate_MovieData() {
        MovieDataRepository.truncate()
    }
}
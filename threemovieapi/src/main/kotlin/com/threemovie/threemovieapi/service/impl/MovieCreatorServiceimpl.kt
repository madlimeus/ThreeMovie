package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.MovieCreator
import com.threemovie.threemovieapi.Repository.MovieCreatorRepository
import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.MovieCreatorService
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieCreatorServiceimpl(
    val MovieCreatorRepository: MovieCreatorRepository
) : MovieCreatorService {

    override fun save_MovieCreator(One_movie_Info: JSONObject, url_Daum_Main: String) {
        val api_movie_data_screening = "api/movie/" + One_movie_Info.get("id").toString() + "/main"
        var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_movie_data_screening)

        val movie_releaseDate =
            JSONObject(One_movie_Info.get("countryMovieInformation").toString()).get("releaseDate").toString()

        val movie_data_array = JSONObject(tmp_data).getJSONArray("casts")

        var casts_items = JSONArray()

        for(One_person in movie_data_array){
            val One_person_json = JSONObject(One_person.toString())
            var json_tmp = JSONObject()

            json_tmp.put("Name", One_person_json.get("nameKorean"))
            json_tmp.put("Role", JSONObject(One_person_json.get("movieJob").toString()).get("role"))
            json_tmp.put("PhotoAddress", One_person_json.get("profileImage"))

            casts_items.put(json_tmp)
        }


        val member_MovieCreator = MovieCreator(
            One_movie_Info.get("titleKorean").toString() + "_" + movie_releaseDate,
            casts_items.toString(),
        )

        val res = MovieCreatorRepository.save(member_MovieCreator)

    }

    override fun turncate_MovieCreator() {
        MovieCreatorRepository.truncate()
    }

}

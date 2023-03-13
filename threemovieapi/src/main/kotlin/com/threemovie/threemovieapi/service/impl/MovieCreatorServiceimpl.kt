package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.MovieCreator
import com.threemovie.threemovieapi.Repository.MovieCreatorRepository
import com.threemovie.threemovieapi.Utils.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import com.threemovie.threemovieapi.service.MovieCreatorService
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MovieCreatorServiceimpl(
    val MovieCreatorRepository: MovieCreatorRepository
) : MovieCreatorService {

    override fun save_MovieCreator(One_movie_Info: JSONObject, url_Daum_Main: String) {
        val api_movie_data_screening = "api/movie/" + One_movie_Info.get("id").toString() + "/main"

        var tmp_data = GET_DATA_USE_DAUM_API(url_Daum_Main + api_movie_data_screening)
        val movie_data_array = JSONObject(tmp_data).getJSONArray("casts")

        val movie_releaseDate =
            JSONObject(One_movie_Info.get("countryMovieInformation").toString()).get("releaseDate").toString()
        val movie_data_json = JSONObject(JSONObject(tmp_data).get("movieCommon").toString())

        val Derector_list: ArrayList<String> = arrayListOf<String>()
        val Derector_photo: ArrayList<String> = arrayListOf<String>()
        val Actor_list: ArrayList<String> = arrayListOf<String>()
        val Actor_photo: ArrayList<String> = arrayListOf<String>()
        val photo_total: ArrayList<String> = arrayListOf<String>()


        for (One_person in movie_data_array) {
            val One_person_json = JSONObject(One_person.toString())
            if (JSONObject(One_person_json.get("movieJob").toString()).get("job").toString() == "감독") {
                Derector_list.add(One_person_json.get("nameKorean").toString())
                Derector_photo.add(One_person_json.get("profileImage").toString())
            } else if (JSONObject(One_person_json.get("movieJob").toString()).get("job").toString() == "주연") {
                Actor_list.add(One_person_json.get("nameKorean").toString())
                Actor_photo.add(One_person_json.get("profileImage").toString())
            }
        }
        photo_total.addAll(Derector_photo)
        photo_total.addAll(Actor_photo)
        val member_MovieCreator = MovieCreator(
            One_movie_Info.get("titleKorean").toString() + "_" + movie_releaseDate,
            Derector_list.toString(),
            Actor_list.toString(),
            photo_total.toString()
        )

        val res = MovieCreatorRepository.save(member_MovieCreator)

    }
}

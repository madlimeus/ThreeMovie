package com.threemovie.threemovieapi.service

import org.json.JSONObject

interface MovieCreatorService {
    fun save_MovieCreator(One_movie_Info : JSONObject, url_Daum_Main: String)
    fun turncate_MovieCreator()

}
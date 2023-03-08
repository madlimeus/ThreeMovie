package com.threemovie.threemovieapi.service

import org.json.JSONObject

interface MovieDataService {
    fun save_MovieData(One_movie_Info: JSONObject, url_Daum_Main: String)
    fun turncate_MovieData()
}
package com.threemovie.threemovieapi.Service

import org.json.JSONObject

interface MovieCreatorService {
	fun save_MovieCreator(One_movie_data: JSONObject, url_Daum_Main: String)
	fun turncate_MovieCreator()
	
}

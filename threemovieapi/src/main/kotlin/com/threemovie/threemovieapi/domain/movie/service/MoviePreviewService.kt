package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.repository.MoviePreviewRepository
import com.threemovie.threemovieapi.global.service.GET_DATA_USE_DAUM_API.Companion.GET_DATA_USE_DAUM_API
import org.json.JSONObject
import org.springframework.stereotype.Service

@Service
class MoviePreviewService(
	val MoviePreviewRepository: MoviePreviewRepository
) {
	fun save_MoviePreview(One_movie_data: JSONObject, url_Daum_Main: String): MoviePreview {
		val api_steelcut_screening = "api/movie/" + One_movie_data.get("id").toString() + "/photoList"
		
		var tmp_data_steelcut = GET_DATA_USE_DAUM_API(url_Daum_Main + api_steelcut_screening)
		val steelcut_Array = JSONObject(tmp_data_steelcut).getJSONArray("contents")
		val movie_releaseDate =
			JSONObject(One_movie_data.get("countryMovieInformation").toString()).get("releaseDate").toString()
		
		val imageUrl_list: ArrayList<String> = arrayListOf<String>()
		var index: Int = 0
		for (One_steelcut in steelcut_Array) {
			val imageUrl = JSONObject(One_steelcut.toString()).get("imageUrl")
			imageUrl_list.add(imageUrl.toString())
			if (index > 5) break
			
			index += 1
		}
		
		val api_preview_screening = "api/video/list/movie/" + One_movie_data.get("id").toString() + "?page=1&size=20"
		
		var tmp_data_preview = GET_DATA_USE_DAUM_API(url_Daum_Main + api_preview_screening)
		val preview_Array = JSONObject(tmp_data_preview).getJSONArray("contents")
		
		val videoUrl_list: ArrayList<String> = arrayListOf<String>()
		for (One_preview in preview_Array) {
			val One_preview_json = JSONObject(One_preview.toString())
			if (One_preview_json.get("title").toString().contains("예고편")) {
				val videoUrl = One_preview_json.get("videoUrl")
				val str_tmp = videoUrl.toString()
				val split_arr_tmp = str_tmp.split("/")
				val iframeUrl =
					"https://play-tv.kakao.com/embed/player/cliplink/${split_arr_tmp[split_arr_tmp.size - 1]}?service=player_share#clipCoverImagePanel"
				videoUrl_list.add(iframeUrl)
			}
		}
		
		var str_imageUrl_list: String?
		var str_videoUrl_list: String?
		
		if (imageUrl_list.size == 0) {
			str_imageUrl_list = null
		} else {
			str_imageUrl_list = imageUrl_list.toString()
		}
		
		if (videoUrl_list.size == 0) {
			str_videoUrl_list = null
		} else {
			str_videoUrl_list = videoUrl_list.toString()
			
		}
		
		val member_MoviePreview = MoviePreview(
			One_movie_data.get("titleKorean").toString() + "_" + movie_releaseDate,
			str_imageUrl_list,
			str_videoUrl_list
		)
		
		return member_MoviePreview
	}
	
	fun turncate_MoviePreview() {
		MoviePreviewRepository.truncate()
	}
	
}

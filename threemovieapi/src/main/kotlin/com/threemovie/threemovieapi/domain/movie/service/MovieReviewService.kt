package com.threemovie.threemovieapi.domain.movie.service

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieReview
import com.threemovie.threemovieapi.domain.movie.repository.MovieReviewRepository
import com.threemovie.threemovieapi.global.service.CalcSimilarity
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class MovieReviewService(
	val movieDataService: MovieDataService,
	val movieReviewRepository: MovieReviewRepository,
) {
	//MB 에서 MovieList 뽑아 오는 과정에서 줄거리에 " 가 들어 가서 JSON 으로 파싱이 안되는 문제 : getMovieListMB
	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	
	var movieList = HashMap<String, String?>()
	
	fun getMovieListDB() {
		val movie = movieDataService.getMovieData()
		for (oneMovie in movie) {
			movieList[oneMovie.movieId] = oneMovie.nameKr
		}
	}
	
	fun usePostApiLC(url: String, paramList: HashMap<String, Any>): String {
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.data("paramList", JSONObject(paramList).toString())
			.ignoreContentType(true)//에러나면 추가
		val doc = conn.post().text()
		return doc
	}
	
	fun usePostApiMB(url: String, paramList: HashMap<String, String>): String {
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.data(paramList)
			.ignoreContentType(true)//에러나면 추가
		val doc = conn.post().text()
		return convertStringUnicodeToKorean(doc)
	}
	
	fun getMovieListLC(): HashMap<String, String> {
		val url = "https://www.lottecinema.co.kr/LCWS/Movie/MovieData.aspx"
		val paramList = HashMap<String, Any>()
		var movieListLC = HashMap<String, String>()
		paramList["MethodName"] = "GetMoviesToBe"
		paramList["channelType"] = "MW"
		paramList["osType"] = "Chrome"
		paramList["osVersion"] =
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
		paramList["multiLanguageID"] = "KR"
		paramList["division"] = 1
		paramList["moviePlayYN"] = "Y"
		paramList["orderType"] = 5
		paramList["blockSize"] = 1000
		paramList["pageNo"] = 1
		paramList["memberOnNo"] = ""
		
		var movieLC = usePostApiLC(url, paramList)
		var arr_movieList_tmp = JSONObject(JSONObject(movieLC).get("Movies").toString()).getJSONArray("Items")
		
		for (json_tmp in arr_movieList_tmp) {
			var similarity: Double = 0.0
			val movieNameLC = JSONObject(json_tmp.toString()).get("MovieNameKR").toString()
			for (arr_tmp in movieList) {
				
				val tmpSimilarity = CalcSimilarity.calcSimilarity(arr_tmp.value, movieNameLC)
				if (similarity < tmpSimilarity) {
					similarity = tmpSimilarity
					if (similarity > 0.7) {
						movieListLC[arr_tmp.key] =
							JSONObject(json_tmp.toString()).get("RepresentationMovieCode").toString()
					}
				}
			}
		}
		return movieListLC
	}
	
	fun getReviewLC(): List<MovieReview> {
		val movieListLT = getMovieListLC()
		val url = "https://www.lottecinema.co.kr/LCWS/Movie/MovieData.aspx"
		val movieReview_List = ArrayList<MovieReview>()
		
		val paramList = HashMap<String, Any>()
		paramList["MethodName"] = "GetReviews"
		paramList["channelType"] = "HO"
		paramList["osType"] = "Chrome"
		paramList["osVersion"] =
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"
		paramList["memberID"] = ""
		paramList["realReviewYN"] = "Y"
		paramList["sortSeq"] = 3
		paramList["pageNo"] = 1
		paramList["pageSize"] = 10
//        println(movieListLT.size)
		for (movie_tmp in movieListLT) {
			paramList["representationMovieCode"] = movie_tmp.value
			val LT_Moviedata = JSONObject(
				JSONObject(usePostApiLC(url, paramList)).get("TotalReviewItems").toString()
			).getJSONArray("Items")
			val movieId = movie_tmp.key
			for (oneReview in LT_Moviedata) {
				val member_MovieReview = MovieReview(

					JSONObject(oneReview.toString()).getInt("RecommandCount"),

					JSONObject(oneReview.toString()).get("RegistDate").toString().replace(".", "").trim().toLong(),
					JSONObject(oneReview.toString()).get("ReviewText").toString(),
					"LC",
				)
				member_MovieReview.movieData = MovieData(movieId)
				movieReview_List.add(member_MovieReview)
			}
		}
		return movieReview_List
	}
	
	fun convertStringUnicodeToKorean(data: String): String {
		val sb = StringBuilder() // 단일 쓰레드이므로 StringBuilder 선언
		var i = 0
		while (i < data.length) {
			if (data[i] == '\\' && data[i + 1] == 'u') {
				val word = data.substring(i + 2, i + 6).toInt(16).toChar()
				sb.append(word)
				i += 5
			} else {
				sb.append(data[i])
			}
			i ++
		}
		return sb.toString()
	}
	
	fun getMovieListMB(): HashMap<String, String> {
		val url = "https://www.megabox.co.kr/on/oh/oha/Movie/selectMovieList.do"
		val paramList = HashMap<String, String>()
		var movieListMB = HashMap<String, String>()
		paramList["currentPage"] = "1"
		paramList["ibxMovieNmSearch"] = ""
		paramList["onairYn"] = "Y"
		paramList["pageType"] = "ticketing"
		paramList["recordCountPerPage"] = "100"
		paramList["specialType"] = ""
		paramList["specialYn"] = "N"
		
		var movieMB = usePostApiMB(url, paramList)
		val split_movieMB = movieMB.split("\"")
		
		var tmpMovieListMB = HashMap<String, String>()
		var movieNo: String = ""
		var movieNm: String = ""
		for (i in 1 until split_movieMB.size) {
			if (split_movieMB[i].equals("movieNo")) {
				movieNo = split_movieMB[i + 2]
			} else if (split_movieMB[i].equals("movieNm")) {
				movieNm = split_movieMB[i + 2]
				tmpMovieListMB[movieNm] = movieNo
			}
		}
		for (movie_tmp in tmpMovieListMB) {
			var similarity: Double = 0.0
			val movieNameMB = movie_tmp.key
			for (arr_tmp in movieList) {
				val tmpSimilarity = CalcSimilarity.calcSimilarity(arr_tmp.value, movieNameMB)
				if (similarity < tmpSimilarity) {
					similarity = tmpSimilarity
				}
				if (similarity > 0.7) {
					movieListMB[arr_tmp.key] = movie_tmp.value
					break
				}
			}
		}
		return movieListMB
	}
	
	fun getReviewMB(): List<MovieReview> {
		val movieListMB = getMovieListMB()
		
		val url = "https://www.megabox.co.kr/on/oh/oha/Movie/selectOneLnList.do"
		
		val movieReview_List = ArrayList<MovieReview>()
		
		val paramList = HashMap<String, String>()
		paramList["currentPage"] = "1"
		paramList["onelnEvalDivCd"] = ""
		paramList["orderCd"] = "one"
		paramList["recordCountPerPage"] = "10"
		
		for (movie_tmp in movieListMB) {
			paramList["movieNo"] = movie_tmp.value
			val movieId = movie_tmp.key
			var MB_Moviedata = JSONArray()
			try {
				MB_Moviedata = JSONObject(usePostApiMB(url, paramList)).getJSONArray("list")
			} catch (e: Exception) {
				println(movieId)
				continue
			}
			
			for (oneReview in MB_Moviedata) {
				val member_MovieReview = MovieReview(

					JSONObject(oneReview.toString()).getInt("rcmmCnt"),
					JSONObject(oneReview.toString()).get("fullLstUptDt").toString().split(" ")[0].replace(".", "")
						.trim().toLong(),
					JSONObject(oneReview.toString()).get("onelnEvalCn").toString(),
					"MB",
				)
				member_MovieReview.movieData = MovieData(movieId)
				movieReview_List.add(member_MovieReview)
			}
		}
		
		return movieReview_List
	}
	
	fun saveReviewData() {
		val reviews = ArrayList<MovieReview>()
		getMovieListDB()
		
		reviews.addAll(getReviewMB())
		reviews.addAll(getReviewLC())
		
		movieReviewRepository.saveAll(reviews)
	}
}

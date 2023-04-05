package com.threemovie.threemovieapi.Utils.Review


import com.threemovie.threemovieapi.Service.impl.MovieInfoServiceimpl
import org.jsoup.Jsoup
import org.springframework.stereotype.Component


@Component
class GetReviewFromTheater(
	val movieInfoService: MovieInfoServiceimpl
) {
	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val CGVurl = "http://www.cgv.co.kr/"
	val mCGVurl = "https://m.cgv.co.kr/"

	fun getOneMovieUrlCGV(movieName: String?): String {
		var result: String?
		val url = CGVurl + "search/?query=${movieName}"
		val conn = Jsoup.connect(url)
		val html = conn.get()
		val movieUrl = html.select("ul.preOrderMovie_list").select("a.img_wrap").attr("href")

		val cgvMovieid = movieUrl.split("=")
		if (cgvMovieid.size > 0) {
			result = cgvMovieid.get(cgvMovieid.size - 1)
		} else {
			result = "no review"
		}
		return result
	}

	fun getMovieListDB(): HashMap<String, String?> {
		val movieList = movieInfoService.getMovieInfo()
		var resultHashMap = HashMap<String, String?>()
		for (oneMovie in movieList) {
			resultHashMap[oneMovie.movieId] = oneMovie.nameKR
		}
		return resultHashMap
	}

	fun getReviewCGV() {
//		val movieList = getMovieListDB()
//		val movieIdx = getOneMovieUrlCGV("스즈메의 문단속")
//
//
//        for (tmp in movieList) {
//            if (! getOneMovieUrlCGV(tmp.value).equals(null)) {
//                val movieIdx = getOneMovieUrlCGV(tmp.value)
//                val url = "https://moviestory.cgv.co.kr/fanpage/movieCommentsList"
//                println("111111111")
//                val paramlist = HashMap<String, String>()
//                paramlist["filterType"] = "0"
//                paramlist["maxCmtIdx"] = "0"
//                paramlist["movieIdx"] = movieIdx
//                paramlist["orderType"] = "1"
//                paramlist["pageSize"] = "10"
//                paramlist["pageStart"] = "1"
//                println("222222222")
//                val conn = Jsoup.connect(url)
//                    .userAgent(userAgent)
//                    .data(paramlist)
//                    .ignoreContentType(true)//에러나면 추가
//                println("333333333")
//
//                val html = conn.post()
//                println(html)
//            }
//        }
		val headers = HashMap<String, String>()
		headers["origin"] = "https://moviestory.cgv.co.kr"
		headers["referer"] = "https://moviestory.cgv.co.kr"
		headers["accept"] = "application/json, text/javascript, */*; q=0.01"
		headers["accept-encoding"] = "gzip, deflate, br"
		headers["accept-language"] = "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7"
		headers["content-type"] = "application/json; charset=UTF-8"
		headers["origin"] = "https://moviestory.cgv.co.kr"
		headers["referer"] = "https://moviestory.cgv.co.kr"
		headers["cookie"] = "JESSIONID=test;"
		headers["x-requested-with"] = "XMLHttpRequest"
		headers["sec-ch-ua"] = "\"Google Chrome\";v=\"111\", \"Not(A:Brand\";v=\"8\", \"Chromium\";v=\"111\""
		headers["sec-ch-ua-mobile"] = "?1"
		headers["sec-ch-ua-platform"] = "\"Android\""
		headers["sec-fetch-mode"] = "cors"
		headers["sec-fetch-site"] = "same-origin"


		val paramlist = HashMap<String, String>()
		paramlist["filterType"] = "0"
		paramlist["maxCmtIdx"] = "10"
		paramlist["movieIdx"] = "86720"
		paramlist["orderType"] = "1"
		paramlist["pageSize"] = "10"
		paramlist["pageStart"] = "1"
		val url = "https://moviestory.cgv.co.kr/fanpage/movieCommentsList"
		val conn = Jsoup.connect(url)
			.headers(headers)
			.userAgent(userAgent)
			.data(paramlist)
			.ignoreContentType(true)

		println("start")
		val html = conn.post()
		println(html)


	}


	fun getReviewCGV_bak() {
		val url = mCGVurl + "WebAPP/MovieV4/movieList.aspx?mtype=now&iPage=1"
		val conn = Jsoup.connect(url)
		val html = conn.get()
		val movieList = html.select("div.main_movie_list.clsAjaxList").select("div.mm_list_item")

		for (tmp in movieList) {
			val split_tmp = tmp.attr("class").split("_")
			val idCGV = split_tmp.get(split_tmp.size - 1)
			println(idCGV)

		}

	}
}

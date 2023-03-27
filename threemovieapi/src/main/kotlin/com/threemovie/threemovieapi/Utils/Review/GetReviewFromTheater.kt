package com.threemovie.threemovieapi.Utils.Review

import com.fasterxml.jackson.databind.ObjectMapper
import com.threemovie.threemovieapi.Entity.MovieInfo
import com.threemovie.threemovieapi.service.impl.MovieInfoServiceimpl
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URI


import java.net.URL
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@Component
class GetReviewFromTheater(
    val movieInfoService: MovieInfoServiceimpl
) {
    val userAgent: String =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
    val CGVurl = "http://www.cgv.co.kr/"
    val mCGVurl = "https://m.cgv.co.kr/"

    fun getOneMovieUrlCGV(movieName: String?) : String{
        var result : String?
        val url = CGVurl+"search/?query=${movieName}"
        val conn = Jsoup.connect(url)
        val html = conn.get()
        val movieUrl = html.select("ul.preOrderMovie_list").select("a.img_wrap").attr("href")

        val cgvMovieid = movieUrl.split("=")
        if(cgvMovieid.size > 0){
            result = cgvMovieid.get(cgvMovieid.size-1)
        } else {
            result = "no review"
        }
        return result
    }

    fun getMovieListDB() : HashMap<String, String?>{
        val movieList = movieInfoService.getMovieInfo()
        var resultHashMap = HashMap<String, String?>()
        for(oneMovie in movieList){
            resultHashMap[oneMovie.movieId] = oneMovie.nameKR
        }
        return resultHashMap
    }

    fun getReviewCGV(){
        val movieList = getMovieListDB()
        val movieIdx = getOneMovieUrlCGV("스즈메의 문단속")

/*
        for(tmp in movieList){
            if(!getOneMovieUrlCGV(tmp.value).equals(null)){
                val movieIdx = getOneMovieUrlCGV(tmp.value)
                val url = "https://moviestory.cgv.co.kr/fanpage/movieCommentsList"
                println("111111111")
                val paramlist = HashMap<String, String>()
                paramlist["filterType"] = "0"
                paramlist["maxCmtIdx"] = "0"
                paramlist["movieIdx"] = movieIdx
                paramlist["orderType"] = "1"
                paramlist["pageSize"] = "10"
                paramlist["pageStart"] = "1"
                println("222222222")
                val conn = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .data(paramlist)
                    .ignoreContentType(true)//에러나면 추가
                println("333333333")

                val html = conn.post()
                println(html)
            }
        }
*/

    }


    fun getReviewCGV_bak(){
        val url = mCGVurl + "WebAPP/MovieV4/movieList.aspx?mtype=now&iPage=1"
        val conn = Jsoup.connect(url)
        val html = conn.get()
        val movieList = html.select("div.main_movie_list.clsAjaxList").select("div.mm_list_item")

        for(tmp in movieList){
            val split_tmp = tmp.attr("class").split("_")
            val idCGV = split_tmp.get(split_tmp.size-1)
            println(idCGV)

        }

    }
}


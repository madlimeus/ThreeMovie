package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.service.ShowTimeService
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val showTimeRepositorySupport: ShowTimeRepositorySupport
) : ShowTimeService {
	override fun getShowTimeAll(): List<ShowTime> {
//		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()
//
//		return showTimeRepositorySupport.getShowTimeAll()

		return emptyList()
	}

	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val mburl = "https://www.megabox.co.kr"

	override fun getShowTime(movieTheater: String): List<ShowTime> {
//		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()
//
//		if (movieTheater == "CGV" || movieTheater == "MegaBOX" || movieTheater == "LotteCinema")
//			getShowingTime(movieTheater)
//		else
//			throw NoSuchElementException("Could not find a $movieTheater")
//
//		return showTimeRepositorySupport.getShowTime(movieTheater)
		val theaters = getMBTheaters()
		getMBShowtimes(theaters)

		return emptyList()
	}

	fun getMBDates(brchNo: String, brchName: String): ArrayList<HashMap<String,String>> {
		val dates = ArrayList<HashMap<String,String>>()
		val url = mburl + "/on/oh/ohc/Brch/schedulePage.do"

		val paramlist = HashMap<String, String>()
		paramlist["brchNm"] = brchName
		paramlist["brchNo"] = brchNo
		paramlist["brchNo1"] = brchNo
		paramlist["firstAt"] = "Y"
		paramlist["masterType"] = "brch"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.data(paramlist)
			.ignoreContentType(true)//에러나면 추가
		val doc = conn.post().text()

		println(doc)
		println(JSONObject(doc).getJSONObject("megaMap"))
		println(JSONObject(doc).getJSONObject("megaMap").getJSONArray("movieFormDeList"))
		val datemap = HashMap<String,String>()
		val showtimes = JSONObject(doc).getJSONObject("megaMap").getJSONArray("movieFormDeList")

		for (i in 0 until showtimes.length()) {
			val showtime = showtimes.getJSONObject(i)
			val date = showtime.getString("playDe")

			println(showtime)
			datemap.put("date",date)
		}

		println(doc)
		println(showtimes)
		return dates
	}

	fun getMBTheaters(): ArrayList<HashMap<String, String>> {
		val theaterlist = ArrayList<HashMap<String, String>>()
		val url = mburl + "/theater/list"

		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
		val doc = conn.get()
		val cities = doc.getElementsByClass("theater-place")[0].getElementsByTag("button")
		val theaters = doc.getElementsByClass("theater-list")

		for (i in 0 until cities.size) {
			val cityName = cities.get(i).getElementsByClass("sel-city")[0].text()
			val theaterDatas = theaters.get(i).getElementsByTag("li")

			for (theater in theaterDatas) {
				val theatermap = HashMap<String, String>()
				val brchNo = theater.attr("data-brch-no")
				val brchName = theater.text()

				theatermap.put("city", cityName)
				theatermap.put("brchNo", brchNo)
				theatermap.put("brchName", brchName)

				theaterlist.add(theatermap)
			}
		}

		return theaterlist
	}

	fun getMBShowtimes(theaters: ArrayList<HashMap<String, String>>): Unit {
		val url = mburl + "/on/oh/ohc/Brch/schedulePage.do"

		for (theater in theaters) {
			val brchName = theater.get("brchName")?:""
			val brchNo = theater.get("brchNo")?:""

			println("${brchNo} ${brchName}")
			val dateninfoes = getMBDates(brchNo, brchName)

			println("창원내서 씹라")
			for (dateninfo in dateninfoes) {
				val date = dateninfo["date"]?:""
				val paramlist = HashMap<String, String>()
				paramlist.put("brchNm", brchName)
				paramlist.put("brchNo", brchNo)
				paramlist.put("brchNo1", brchNo)
				paramlist.put("masterType", "brch")
				paramlist.put("playDe", date)
				paramlist.put("firstAt", "N")
				val conn = Jsoup.connect(url)
					.userAgent(userAgent)
					.data(paramlist)
					.ignoreContentType(true)//에러나면 추가
				val doc = conn.post().body().text()
				val showtimes = JSONObject(doc).getJSONObject("megaMap").getJSONArray("movieFormList")

				println("${brchNo} ${brchName} ${date}")
				for (i in 0 until showtimes.length()) {
					val showtime = showtimes.getJSONObject(i)
					val totalSeat = showtime.getInt("totSeatCnt")
					val restSeat = showtime.getInt("restSeatCnt")
					val playSchldNo = showtime.getString("playSchdlNo")
					val startTime = showtime.getString("playStartTime")
					val endTime = showtime.getString("playEndTime")
					val runnigTime = showtime.getString("moviePlayTime")
					val movieNm = showtime.getString("rpstMovieNm")
					val movieNmEN = showtime.get("movieEngNm")?: ""
					val playKind = showtime.getString("playKindNm")
					val ExpoNm = showtime.getString("theabExpoNm")
					val ticketPage = "https://www.megabox.co.kr/bookingByPlaySchdlNo?playSchdlNo=${playSchldNo}"

					println("${movieNm} ${movieNmEN} ${playKind} ${ExpoNm} ${ticketPage}\n${totalSeat} ${restSeat}\n${startTime} ${endTime} ${runnigTime}\n")
				}
			}
		}
	}


	fun getShowingTime(movieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(movieTheater)
}

package com.threemovie.threemovieapi.Utils

import com.threemovie.threemovieapi.Repository.ShowTimeRepository
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.UpdateTimeRepository
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class ShowingTimeScheduler(
	val updateTimeRepository: UpdateTimeRepository,
	val updateTimeRepositorySupport: UpdateTimeRepositorySupport,
	val showTimeRepository: ShowTimeRepository,
	val showTimeRepositorySupport: ShowTimeRepositorySupport
) {
	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val CGVurl = "http://www.cgv.co.kr"
	val LCurl = "http://www.lottecinema.co.kr"
	val mburl = "https://www.megabox.co.kr"

//	@Async
//	@Scheduled(cron = "0/60 * * * * ?")
//	fun ChkMovieShowingTime() {
//		if (ChkNeedUpdate.chkMovieShowingTime(updateTimeRepositorySupport.getMovieShowingTime().toLong())) {
//			updateCGVShowingTime()
//			updateMegaBOXShowingTime()
//			updateLotteCinemaShowingTime()
//		}
//	}

	fun getMBDatesNTheaters(brchNo: String, brchName: String): Pair<Triple<String, String, String>, ArrayList<String>> {
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
		val doc = conn.post().text().replace("\"\"", "\"")

		val datemap = ArrayList<String>()
		val megamap = JSONObject(doc).getJSONObject("megaMap")
		val showtimes = megamap.getJSONArray("movieFormDeList")
		val theaterinfo = megamap.getJSONObject("brchInfo")

		val addr = theaterinfo.getString("roadNmAddr")
		val addrEN = theaterinfo.getString("engAddr")
		val brchEN = theaterinfo.getString("brchEngNm")

		for (i in 0 until showtimes.length()) {
			val showtime = showtimes.getJSONObject(i)
			val date = showtime.getString("playDe")

			datemap.add(date)
		}

		return Pair(Triple(addr, addrEN, brchEN), datemap)
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

				theatermap["city"] = cityName
				theatermap["brchNo"] = brchNo
				theatermap["brchName"] = brchName

				theaterlist.add(theatermap)
			}
		}

		return theaterlist
	}

	fun getMBShowtimes(theaters: ArrayList<HashMap<String, String>>): Unit {
		val url = mburl + "/on/oh/ohc/Brch/schedulePage.do"

		for (theater in theaters) {
			val brchKR = theater["brchName"] ?: ""
			val brchNo = theater["brchNo"] ?: ""

			val (theater, dates) = getMBDatesNTheaters(brchNo, brchKR)
			val (addr, addrEN, brchEN) = theater
			val city = addr.substring(0..1)

			println("${city} ${brchKR} ${brchEN} ${brchNo}\n${addr}\n${addrEN}")

			for (date in dates) {
				val paramlist = HashMap<String, String>()
				paramlist["brchNm"] = brchKR
				paramlist["brchNo"] = brchNo
				paramlist["brchNo1"] = brchNo
				paramlist["masterType"] = "brch"
				paramlist["playDe"] = date
				paramlist["firstAt"] = "N"
				val conn = Jsoup.connect(url)
					.userAgent(userAgent)
					.data(paramlist)
					.ignoreContentType(true)//에러나면 추가
				val doc = conn.post().body().text()
				val showtimes = JSONObject(doc).getJSONObject("megaMap").getJSONArray("movieFormList")

				println("${date}")
				for (i in 0 until showtimes.length()) {
					val showtime = showtimes.getJSONObject(i)
					val totalSeat = showtime.getInt("totSeatCnt")
					val restSeat = showtime.getInt("restSeatCnt")
					val playSchldNo = showtime.getString("playSchdlNo")
					val startTime = showtime.getString("playStartTime")
					val endTime = showtime.getString("playEndTime")
					val runnigTime = showtime.getString("moviePlayTime")
					val movieNm = showtime.getString("rpstMovieNm")
					val movieNmEN = showtime.get("movieEngNm") ?: ""
					val playKind = showtime.getString("playKindNm")
					val screenKR = showtime.getString("theabExpoNm")
					val screenEN = showtime.getString("theabEngNm")
					val ticketPage = "https://www.megabox.co.kr/bookingByPlaySchdlNo?playSchdlNo=${playSchldNo}"

					println("${movieNm} ${movieNmEN} ${playKind}\n${screenKR} ${screenEN}\n${startTime} ${endTime} ${runnigTime}\n${totalSeat} ${restSeat} ${ticketPage}")
				}
			}
		}
	}

	fun getLCAddr(divisionCode: String, detailDivisionCode: String, cinemaID: String): String {
		val url: String =
			LCurl + "/LCWS/Cinema/CinemaData.aspx"

		var paramlist = HashMap<String, Any>()
		paramlist["MethodName"] = "GetCinemaDetailItem"
		paramlist["channelType"] = "HO"
		paramlist["osType"] = "Chrome"
		paramlist["osVersion"] = userAgent
		paramlist["divisionCode"] = divisionCode
		paramlist["detailDivisionCode"] = detailDivisionCode
		paramlist["cinemaID"] = cinemaID
		paramlist["memberOnNo"] = "0"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.data("ParamList", JSONObject(paramlist).toString())
		val doc = conn.post().body().text()
		val data = JSONObject(doc)

		val Addr = data.getJSONObject("CinemaDetail").getString("Address")

		return Addr
	}

	fun getLCTheaters(): ArrayList<HashMap<String, Any>> {
		val theaterlist = ArrayList<HashMap<String, Any>>()
		val url: String =
			LCurl + "/LCWS/Cinema/CinemaData.aspx"

		var paramlist = HashMap<String, Any>()
		paramlist["MethodName"] = "GetCinemaItems"
		paramlist["channelType"] = "HO"
		paramlist["osType"] = "W"
		paramlist["osVersion"] = userAgent
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.data("ParamList", JSONObject(paramlist).toString())
		val doc = conn.post().body().text()
		val data = JSONObject(doc)

		val cinemas = data.getJSONObject("Cinemas").getJSONArray("Items")

		for (i in 0 until cinemas.length()) {
			val cinema = cinemas.getJSONObject(i)
			val brchKR = cinema.getString("CinemaName")
			val brchEN = cinema.getString("CinemaNameUS")
			val divisionCode = cinema.get("DivisionCode").toString()
			val detailDivisionCode = cinema.getString("DetailDivisionCode")
			val cinemaID = cinema.get("CinemaID").toString()

			if (divisionCode == "2")
				continue

			val theatermap = HashMap<String, Any>()
			theatermap["brchEN"] = brchEN
			theatermap["brchKR"] = brchKR
			theatermap["divisionCode"] = divisionCode
			theatermap["detailDivisionCode"] = detailDivisionCode
			theatermap["cinemaID"] = cinemaID
			var addr = getLCAddr(divisionCode, detailDivisionCode, cinemaID)
			if (brchKR == "춘천")
				addr = "강원도 춘천시 중앙로67번길 18 (죽림동)"
			theatermap["addr"] = addr
			theatermap["city"] = addr.substring(0..1)

			theaterlist.add(theatermap)
		}

		return theaterlist
	}

	fun getLCTicketAddr(data: JSONObject): String {
		val screenID = data.get("ScreenID").toString()
		val cinemaCode = data.get("CinemaID").toString()
		val movieCd = data.getString("MovieCode")
		val date = data.getString("PlayDt")
		val startTime = data.getString("StartTime")
		val ticketPage =
			LCurl + "/NLCHS/ticketing?link_screenId=${screenID}&link_cinemaCode=${cinemaCode}&link_movieCd=${movieCd}&link_date=${date}&link_time=${startTime}&link_channelCode=naver"

		return ticketPage
	}

	fun getLCDates(theatercode: String): ArrayList<String> {
		var datelist = ArrayList<String>()
		val url: String =
			LCurl + "/LCWS/Ticketing/TicketingData.aspx"

		var paramlist = HashMap<String, Any>()
		paramlist["MethodName"] = "GetInvisibleMoviePlayInfo"
		paramlist["channelType"] = "HO"
		paramlist["osType"] = "W"
		paramlist["osVersion"] = userAgent
		paramlist["cinemaList"] = theatercode
		paramlist["movieCd"] = ""
		paramlist["playDt"] = "2023-03-03"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.data("ParamList", JSONObject(paramlist).toString())
		val doc = conn.post().body().text()
		val data = JSONObject(doc).getJSONObject("PlayDates").getJSONArray("Items")

		for (i in 0 until data.length()) {
			val playdate = data.getJSONObject(i).getString("PlayDate").split(" ")
			datelist.add(playdate[0])
		}

		return datelist
	}

	fun getRunnigTime(startTime: String, endTime: String): Int {
		val end = endTime.split(":")
		val start = startTime.split(":")

		return end[0].toInt() * 60 + end[1].toInt() - start[0].toInt() * 60 - start[1].toInt()
	}

	fun getLCShowtimes(theaterlist: ArrayList<HashMap<String, Any>>): Unit {
		for (theater in theaterlist) {
			val brchKR = theater["brchKR"]
			val brchEN = theater["brchEN"]
			val divisionCode = theater["divisionCode"].toString() ?: ""
			val detailDivisionCode = theater["detailDivisionCode"].toString() ?: ""
			val cinemaID = theater["cinemaID"].toString() ?: ""
			val cinemaCode: String =
				"${divisionCode}|${detailDivisionCode}|${cinemaID}"
			val datelist = getLCDates(cinemaCode)
			val addr = theater["addr"]
			val city = theater["city"]

			println("${city} ${brchKR} ${brchEN} ${cinemaCode} \n${addr}")
			for (date in datelist) {
				val url: String =
					LCurl + "/LCWS/Ticketing/TicketingData.aspx"
				var paramlist = HashMap<String, String>()
				paramlist["MethodName"] = "GetPlaySequence"
				paramlist["channelType"] = "HO"
				paramlist["osType"] = "W"
				paramlist["osVersion"] = userAgent
				paramlist["playDate"] = date
				paramlist["cinemaID"] = cinemaCode
				paramlist["representationMovieCode"] = ""
				val conn = Jsoup.connect(url)
					.userAgent(userAgent)
					.data("ParamList", JSONObject(paramlist).toString())
				val doc = conn.post().body().text()
				val data = JSONObject(doc)
				val playSeqs = data.getJSONObject("PlaySeqs").getJSONArray("Items")
				val playHeaders = data.getJSONObject("PlaySeqsHeader").getJSONArray("Items")
				val translation = HashMap<Int, String>()

				println("${date}")

				for (i in 0 until playHeaders.length()) {
					val headerdata = playHeaders.getJSONObject(i)
					val translationCode = headerdata.getInt("TranslationDivisionCode")
					val translationName = headerdata.getString("TranslationDivisionNameKR")
					translation[translationCode] = translationName
				}

				for (i in 0 until playSeqs.length()) {
					val playdata = playSeqs.getJSONObject(i)

					val movieNameKR = playdata.get("MovieNameKR")
					val movieNameUS = playdata.get("MovieNameUS")
					val screenDivisionCode = playdata.getInt("ScreenDivisionCode")
					var screenDivisionKR = ""
					var screenDivisionEN = ""
					if (screenDivisionCode != 100 && playdata.getString("ScreenDivisionNameKR") != playdata.get("ScreenNameKR")) {
						screenDivisionKR = playdata.getString("ScreenDivisionNameKR") + " "
						screenDivisionEN = playdata.getString("ScreenDivisionNameUS") + " "
					}
					val screenKR = screenDivisionKR + playdata.get("ScreenNameKR")
					val screenEN = screenDivisionEN + playdata.get("ScreenNameUS")

					val startTime = playdata.getString("StartTime")
					val endTime = playdata.getString("EndTime")
					val totalSeat = playdata.get("TotalSeatCount")
					val restSeat = playdata.get("BookingSeatCount")
					val translationCode = playdata.getInt("TranslationDivisionCode")
					val runnigTime = getRunnigTime(startTime, endTime)

					var playKind = playdata.getString("FilmNameKR")
					if (translationCode != 900)
						playKind + "(${translation[translationCode]})"

					val ticketPage = getLCTicketAddr(playdata)

					println("${movieNameKR} ${movieNameUS} ${playKind}\n${screenKR} ${screenEN}\n${startTime} ${endTime} ${runnigTime}\n${totalSeat} ${restSeat} ${ticketPage}")
				}
			}
		}
	}
}

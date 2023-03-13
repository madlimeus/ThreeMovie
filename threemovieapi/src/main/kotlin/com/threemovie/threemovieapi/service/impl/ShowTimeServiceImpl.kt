package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Entity.ShowTimeMovieInfo
import com.threemovie.threemovieapi.Entity.TheaterData
import com.threemovie.threemovieapi.Repository.ShowTimeRepository
import com.threemovie.threemovieapi.Repository.Support.MovieInfoRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.service.ShowTimeService
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.regex.Matcher
import java.util.regex.Pattern

@Service
class ShowTimeServiceImpl(
	val showTimeRepositorySupport: ShowTimeRepositorySupport,
	val movieInfoRepositorySupport: MovieInfoRepositorySupport,
	val showTimeRepository: ShowTimeRepository,
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
) : ShowTimeService {
	override fun getShowTimeAll(): List<ShowTime> {
//		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()
//
//		return showTimeRepositorySupport.getShowTimeAll()

		return emptyList()
	}

	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val CGVurl = "http://www.cgv.co.kr"
	val LCurl = "http://www.lottecinema.co.kr"
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
		showTimeRepository.truncateShowTime()
		val showTimeMovieInfo = movieInfoRepositorySupport.getShowTimeMovieInfo()
		val movieInfoMap = chgListTOMap(showTimeMovieInfo)
		val mbTheaters = theaterDataRepositorySupport.getTheaterData("MB")
		val lcTheaters = theaterDataRepositorySupport.getTheaterData("LC")
		val cgvTheaters = theaterDataRepositorySupport.getTheaterData("CGV")

		val startTime = LocalDateTime.now()
		var showTimeList = getMBShowtimes(mbTheaters, movieInfoMap)
		showTimeList += getLCShowtimes(lcTheaters, movieInfoMap)
		showTimeList += getCGVShowtimes(cgvTheaters, movieInfoMap)


		showTimeRepository.saveAll(showTimeList)
		val endTime = LocalDateTime.now()
		println(startTime)
		println(endTime)
		return emptyList()
	}

	fun chgListTOMap(showTimeMovieInfo: List<ShowTimeMovieInfo>): HashMap<String, Pair<String, String>> {
		var movieInfoMap = HashMap<String, Pair<String, String>>()

		for (info in showTimeMovieInfo) {
			movieInfoMap[info.NameKR] = Pair(info.MovieId, info.NameEN)
		}
		return movieInfoMap
	}

	fun getMBDates(brchNo: String, brchName: String): ArrayList<String> {
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


		for (i in 0 until showtimes.length()) {
			val showtime = showtimes.getJSONObject(i)
			val date = showtime.getString("playDe")

			datemap.add(date)
		}

		return datemap
	}

	fun getMBShowtimes(
		theaters: List<TheaterData>,
		movieInfoMap: HashMap<String, Pair<String, String>>
	): List<ShowTime> {
		var showTimeList = ArrayList<ShowTime>()
		val url = mburl + "/on/oh/ohc/Brch/schedulePage.do"
		for (theater in theaters) {
			val brchKR = theater.brchKR
			val brchEN = theater.brchEN
			val city = theater.city

			val brchNo = theater.theaterCode

			val dates = getMBDates(brchNo, brchKR)

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

				for (i in 0 until showtimes.length()) {
					val showtime = showtimes.getJSONObject(i)
					val totalSeat = showtime.getInt("totSeatCnt")
					val restSeat = showtime.getInt("restSeatCnt")
					val playSchldNo = showtime.getString("playSchdlNo")
					val startTime = showtime.getString("playStartTime")
					val endTime = showtime.getString("playEndTime")
					val runningTime = showtime.getString("moviePlayTime").toInt()
					val movieKR = showtime.getString("rpstMovieNm")
					val playKind = showtime.getString("playKindNm")
					val screenKR = showtime.getString("theabExpoNm")
					val screenEN = showtime.getString("theabEngNm")
					val ticketPage = "https://www.megabox.co.kr/bookingByPlaySchdlNo?playSchdlNo=${playSchldNo}"

					val movieInfo = movieInfoMap[movieKR]

					val movieId = movieInfo?.first ?: movieKR
					val movieEN = movieInfo?.second ?: ""

					val tmp = ShowTime(
						movieId,
						"MB",
						city,
						brchKR,
						brchEN,
						movieKR,
						movieEN,
						screenKR,
						screenEN,
						date,
						startTime,
						endTime,
						runningTime,
						totalSeat,
						restSeat,
						playKind,
						ticketPage
					)

					showTimeList.add(tmp)
				}
			}
		}
		return showTimeList
	}

	fun getLCTicketAddr(data: JSONObject): String {
		val screenID = data.get("ScreenID").toString()
		val cinemaID = data.get("CinemaID").toString()
		val movieCd = data.getString("MovieCode")
		val date = data.getString("PlayDt")
		val startTime = data.getString("StartTime")
		val ticketPage =
			LCurl + "/NLCHS/ticketing?link_screenId=${screenID}&link_cinemaCode=${cinemaID}&link_movieCd=${movieCd}&link_date=${date}&link_time=${startTime}&link_channelCode=naver"

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

	fun getLCShowtimes(
		theaters: List<TheaterData>,
		movieInfoMap: HashMap<String, Pair<String, String>>
	): List<ShowTime> {
		var showTimeList = ArrayList<ShowTime>()
		for (theater in theaters) {
			val brchKR = theater.brchKR
			val brchEN = theater.brchEN
			val city = theater.city
			val cinemaCode = theater.theaterCode
			val datelist = getLCDates(cinemaCode)

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

				for (i in 0 until playHeaders.length()) {
					val headerdata = playHeaders.getJSONObject(i)
					val translationCode = headerdata.getInt("TranslationDivisionCode")
					val translationName = headerdata.getString("TranslationDivisionNameKR")
					translation[translationCode] = translationName
				}

				for (i in 0 until playSeqs.length()) {
					val playdata = playSeqs.getJSONObject(i)

					val movieKR = playdata.getString("MovieNameKR")
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
					val totalSeat = playdata.getInt("TotalSeatCount")
					val restSeat = playdata.getInt("BookingSeatCount")
					val translationCode = playdata.getInt("TranslationDivisionCode")
					val runningTime = getRunnigTime(startTime, endTime)

					var playKind = playdata.getString("FilmNameKR")
					if (translationCode != 900)
						playKind + "(${translation[translationCode]})"

					val ticketPage = getLCTicketAddr(playdata)

					val movieInfo = movieInfoMap[movieKR]

					val movieId = movieInfo?.first ?: movieKR
					val movieEN = movieInfo?.second ?: ""

					val tmp = ShowTime(
						movieId,
						"LC",
						city,
						brchKR,
						brchEN,
						movieKR,
						movieEN,
						screenKR,
						screenEN,
						date,
						startTime,
						endTime,
						runningTime,
						totalSeat,
						restSeat,
						playKind,
						ticketPage
					)

					showTimeList.add(tmp)
				}
			}
		}
		return showTimeList
	}

	fun getCGVDates(theatercode: String): ArrayList<String> {
		val url: String =
			CGVurl + "/common/showtimes/iframeTheater.aspx?theatercode=${theatercode}"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.referrer(
				CGVurl
			)
			.header(
				"Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
			)
			.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
			.header(
				"Cookie",
				"ASP.NET_SessionId=test;"
			)
		val doc = conn.get()

		val days = doc.getElementsByClass("day")
		var datelist = ArrayList<String>()

		for (day in days) {
			val href = day.getElementsByTag("a")[0].attr("href")
			val pattern: Pattern = Pattern.compile(".*date=([^&]+).*")
			val matcher: Matcher = pattern.matcher(href)
			if (matcher.find()) {
				datelist.add(matcher.group(1))
			}
		}

		return datelist
	}

	fun chgStrtoTimestr(str: String): String {
		return "${str.substring(0..1)}:${str.substring(2..3)}"
	}

	fun chgScreenKRtoEN(screenKR: String): String {
		var screenEN: String = screenKR
		if ("관" in screenKR)
			screenEN = "CINEMA ${screenEN.replace("관", "")}"

		return screenEN
	}

	fun getCGVShowtimes(
		theaters: List<TheaterData>,
		movieInfoMap: HashMap<String, Pair<String, String>>
	): List<ShowTime> {
		var showTimeList = ArrayList<ShowTime>()
		for (theater in theaters) {
			val theaterCode = theater.theaterCode
			val brchKR = theater.brchKR
			val brchEN = theater.brchEN
			val city = theater.city
			val datelist = getCGVDates(theaterCode)

			for (date in datelist) {
				val url: String =
					CGVurl + "/common/showtimes/iframeTheater.aspx?theatercode=${theaterCode}&date=${date}"
				val conn = Jsoup.connect(url)
					.userAgent(userAgent)
					.referrer(
						CGVurl
					)
					.header(
						"Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"
					)
					.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
					.header(
						"Cookie",
						"ASP.NET_SessionId=test;"
					)
				val doc = conn.get()
				val showtimes = doc.getElementsByClass("col-times")

				for (showtime in showtimes) {
					val infoMovie = showtime.getElementsByClass("info-movie")[0]
					val movieKR = infoMovie.getElementsByTag("a")[0].text()
					val runningTime = infoMovie.getElementsByTag("i")[2].text().replace("분", "").toInt()

					val typeHalls = showtime.getElementsByClass("type-hall")
					for (typeHall in typeHalls) {
						val infoHall = typeHall.getElementsByClass("info-hall")[0]
						val playKind = infoHall.getElementsByTag("li")[0].text()
						val screenKR = infoHall.getElementsByTag("li")[1].text()
						val screenEN = chgScreenKRtoEN(screenKR)
						val totalSeat =
							infoHall.getElementsByTag("li")[2].text().replace("[^0-9]+".toRegex(), "").toInt()

						val infoTimeTable = typeHall.getElementsByClass("info-timetable")[0]
						val timelist = infoTimeTable.getElementsByTag("li")

						for (timeinfo in timelist) {
							val datas = timeinfo.getElementsByTag("a")

							if (datas.isEmpty() || datas[0].attr("href") == "/") {
								continue
							}

							val startTime = chgStrtoTimestr(datas[0].attr("data-playstarttime"))
							val endTime = chgStrtoTimestr(datas[0].attr("data-playendtime"))
							val ticketPage = CGVurl + datas[0].attr("href")
							val restSeat = datas[0].attr("data-seatremaincnt").toInt()

							val movieInfo = movieInfoMap[movieKR]

							val movieId = movieInfo?.first ?: movieKR
							val movieEN = movieInfo?.second ?: ""
							val datestr = "${date.substring(0..1)}:${date.substring(2..3)}"

							val tmp = ShowTime(
								movieId,
								"LC",
								city,
								brchKR,
								brchEN,
								movieKR,
								movieEN,
								screenKR,
								screenEN,
								datestr,
								startTime,
								endTime,
								runningTime,
								totalSeat,
								restSeat,
								playKind,
								ticketPage
							)

							showTimeList.add(tmp)
						}
					}
				}
			}
		}
		return showTimeList
	}


	fun getShowingTime(movieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(movieTheater)
}

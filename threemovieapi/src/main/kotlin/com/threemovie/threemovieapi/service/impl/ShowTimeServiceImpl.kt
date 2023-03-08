package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.service.ShowTimeService
import org.json.JSONArray
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.util.regex.Matcher
import java.util.regex.Pattern

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
		getCGVShowtimes(getCGVTheaters(), getCGVBrchsEN())

		return emptyList()
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

	fun getCGVBrchsEN(): HashMap<String, String> {
		val url: String =
			CGVurl + "/reserve/show-times/eng/"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
		val doc = conn.get()
		val scripts = doc.select("script")

		var theatersData: String = ""

		for (script in scripts) {
			if (script.data().contains("var theaterJsonData")) {
				val pattern: Pattern = Pattern.compile(".*var theaterJsonData = ([^;]*);")
				val matcher: Matcher = pattern.matcher(script.data())
				if (matcher.find()) {
					theatersData = matcher.group(1)
					break
				}
			}
		}

		val jsonArray = JSONArray(theatersData)
		val brchsEN = HashMap<String, String>()

		for (i in 0 until jsonArray.length()) {
			val theaters = jsonArray.getJSONObject(i)

			val areaTheaterList = theaters.optJSONArray("AreaTheaterDetailList")

			for (j in 0 until areaTheaterList.length()) {
				val theater = areaTheaterList.getJSONObject(j)
				val theaterCode = theater.getString("TheaterCode")
				val theaterName = theater.getString("TheaterName")

				brchsEN[theaterCode] = theaterName
			}
		}

		return brchsEN
	}

	fun getCGVTheaters(): ArrayList<HashMap<String, String>> {
		val url: String =
			CGVurl + "/theaters/"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
		val doc = conn.get()
		val scripts = doc.select("script")

		var theatersData: String = ""

		for (script in scripts) {
			if (script.data().contains("var theaterJsonData")) {
				val pattern: Pattern = Pattern.compile(".*var theaterJsonData = ([^;]*);")
				val matcher: Matcher = pattern.matcher(script.data())
				if (matcher.find()) {
					theatersData = matcher.group(1)
					break
				}
			}
		}

		val jsonArray = JSONArray(theatersData)
		val theaterlist = ArrayList<HashMap<String, String>>()

		for (i in 0 until jsonArray.length()) {
			val theaters = jsonArray.getJSONObject(i)

			val areaTheaterList = theaters.optJSONArray("AreaTheaterDetailList")

			for (j in 0 until areaTheaterList.length()) {
				val theater = areaTheaterList.getJSONObject(j)
				val theaterCode = theater.getString("TheaterCode")
				val theaterName = theater.getString("TheaterName")
				val addr = getCGVAddress(theaterCode)
				val city = addr.substring(0..1)

				val theatermap = HashMap<String, String>()

				theatermap["theaterCode"] = theaterCode
				theatermap["brchKR"] = theaterName
				theatermap["addr"] = addr
				theatermap["city"] = city

				theaterlist.add(theatermap)
			}
		}

		return theaterlist
	}

	fun getCGVAddress(theatercode: String): String {
		val url: String =
			"https://m.cgv.co.kr/WebApp/TheaterV4/TheaterDetail.aspx?tc=${theatercode}"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
		val doc = conn.get()
		val addr = doc.getElementsByClass("info_map")[0].getElementsByClass("line")[0].text()

		return addr
	}

	fun chgStrtoDatestr(str: String): String {
		return "${str.substring(0..1)}:${str.substring(2..3)}"
	}

	fun chgScreenKRtoEN(screenKR: String): String {
		var screenEN: String = screenKR
		if ("관" in screenKR)
			screenEN = "CINEMA ${screenEN.replace("관", "")}"

		return screenEN
	}

	fun getCGVShowtimes(theaterlist: ArrayList<HashMap<String, String>>, brchsEN: HashMap<String, String>): Unit {
		for (theater in theaterlist) {
			val theaterCode = theater["theaterCode"] ?: ""
			val brchKR = theater["brchKR"] ?: ""
			val brchEN = brchsEN[theaterCode] ?: ""
			val addr = theater["addr"] ?: ""
			val city = theater["city"] ?: ""
			val datelist = getCGVDates(theaterCode)
			println("${city} ${brchKR} ${brchEN}\n${addr}")
			for (date in datelist) {
				println(date)
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
					val movieName = infoMovie.getElementsByTag("a")[0].text()
					val runningTime = infoMovie.getElementsByTag("i")[2].text().replace("분", "")

					val typeHalls = showtime.getElementsByClass("type-hall")
					for (typeHall in typeHalls) {
						val infoHall = typeHall.getElementsByClass("info-hall")[0]
						val playKind = infoHall.getElementsByTag("li")[0].text()
						val screenKR = infoHall.getElementsByTag("li")[1].text()
						val screenEN = chgScreenKRtoEN(screenKR)
						val allSeats = infoHall.getElementsByTag("li")[2].text().replace("[^0-9]+".toRegex(), "")

						val infoTimeTable = typeHall.getElementsByClass("info-timetable")[0]
						val timeinfoes = infoTimeTable.getElementsByTag("li")

						for (timeinfo in timeinfoes) {
							val datas = timeinfo.getElementsByTag("a")
							var starttime = ""
							var endtime = ""
							var ticketPage = ""
							var seatsLeft = ""
							if (datas.isEmpty() || datas[0].attr("href") == "/") {
								starttime = timeinfo.getElementsByTag("em")[0].text()
								seatsLeft = "마감|매진"
							} else {
								starttime = chgStrtoDatestr(datas[0].attr("data-playstarttime"))
								endtime = chgStrtoDatestr(datas[0].attr("data-playendtime"))
								ticketPage = CGVurl + datas[0].attr("href")
								seatsLeft = datas[0].attr("data-seatremaincnt")
							}


							println("${movieName} ${playKind}\n${screenKR} ${screenEN}\n${starttime} ${endtime} ${runningTime}\n${allSeats} ${seatsLeft}\n${ticketPage}")
						}
					}
				}
			}
		}
	}

	fun getShowingTime(movieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(movieTheater)
}

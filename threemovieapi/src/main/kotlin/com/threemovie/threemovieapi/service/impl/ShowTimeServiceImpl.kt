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
	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val CGVurl = "http://www.cgv.co.kr"
	override fun getShowTime(movieTheater: String): List<ShowTime> {
//		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()
//
//		if (movieTheater == "CGV" || movieTheater == "MegaBOX" || movieTheater == "LotteCinema")
//			getShowingTime(movieTheater)
//		else
//			throw NoSuchElementException("Could not find a $movieTheater")
//
//		return showTimeRepositorySupport.getShowTime(movieTheater)
		val theaterlist = getCGVTheaterData()
		getCGVShowingTime(theaterlist)

		return emptyList()
	}

	override fun getShowTimeAll(): List<ShowTime> {
////		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()
//
//		return showTimeRepositorySupport.getShowTimeAll()
		return emptyList()
	}

	fun getCGVDateList(theatercode: String): ArrayList<String> {
		val url: String =
			"http://www.cgv.co.kr/common/showtimes/iframeTheater.aspx?theatercode=${theatercode}"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
			.referrer(
				"http://www.cgv.co.kr/theaters/"
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

	fun getCGVTheaterData(): ArrayList<Triple<String, String, String>> {
		val url: String =
			"http://www.cgv.co.kr/theaters/"
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
		val theaterlist = ArrayList<Triple<String, String, String>>()

		for (i in 0 until jsonArray.length()) {
			val theaters = jsonArray.getJSONObject(i)
			val regionName = theaters.getString("RegionName")
			val areaTheaterList = theaters.optJSONArray("AreaTheaterDetailList")

			for (j in 0 until areaTheaterList.length()) {
				val theater = areaTheaterList.getJSONObject(j)
				val theaterCode = theater.getString("TheaterCode")
				val theaterName = theater.getString("TheaterName")

				theaterlist.add(Triple(regionName, theaterCode, theaterName))
			}
		}

		return theaterlist
	}

	fun getCGVShowingTime(theaterlist: ArrayList<Triple<String, String, String>>): Unit {
		for (theater in theaterlist) {
			val datelist = getCGVDateList(theater.second)
			for (date in datelist) {
				println("${theater.first} ${theater.third} ${date}")
				val url: String =
					"http://www.cgv.co.kr/common/showtimes/iframeTheater.aspx?theatercode=${theater.second}&date=${date}"
				val conn = Jsoup.connect(url)
					.userAgent(userAgent)
					.referrer(
						"http://www.cgv.co.kr/theaters/"
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
					val age = infoMovie.getElementsByTag("i")[0].text()
					val categories = infoMovie.getElementsByTag("i")[1].text()
					val runningTime = infoMovie.getElementsByTag("i")[2].text()
					val comeOut = infoMovie.getElementsByTag("i")[3].text().replace("[^0-9]+".toRegex(), "")


					val typeHalls = showtime.getElementsByClass("type-hall")
					for (typeHall in typeHalls) {
						val infoHall = typeHall.getElementsByClass("info-hall")[0]
						val dimension = infoHall.getElementsByTag("li")[0].text()
						val whereTheater = infoHall.getElementsByTag("li")[1].text()
						val allSeats = infoHall.getElementsByTag("li")[2].text().replace("[^0-9]+".toRegex(), "")


						val infoTimeTable = typeHall.getElementsByClass("info-timetable")[0]
						val timeinfoes = infoTimeTable.getElementsByTag("li")

						for (timeinfo in timeinfoes) {
							val datas = timeinfo.getElementsByTag("a")
							var starttime = ""
							var href = ""
							var seatsLeft = ""
							if (datas.isEmpty()) {
								starttime = timeinfo.getElementsByTag("em")[0].text().replace(":", "")
								seatsLeft = "마감"
							} else {
								starttime = datas[0].attr("data-playstarttime")
								href = CGVurl + datas[0].attr("href")
								seatsLeft = datas[0].attr("data-seatremaincnt")
							}


							println("${movieName} ${age}세 ${categories} ${runningTime} ${comeOut} ${dimension} ${whereTheater} ${allSeats} ${starttime} ${href} ${seatsLeft}좌석 남음")
						}
					}
				}
			}
		}

	}

	fun getShowingTime(movieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(movieTheater)
}

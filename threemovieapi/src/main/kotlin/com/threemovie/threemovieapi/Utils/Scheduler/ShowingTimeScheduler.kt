package com.threemovie.threemovieapi.Utils.Scheduler

import com.threemovie.threemovieapi.Entity.DTO.ShowTimeBranchDTO
import com.threemovie.threemovieapi.Entity.MovieNameData
import com.threemovie.threemovieapi.Entity.TheaterData
import com.threemovie.threemovieapi.Entity.TmpShowTime
import com.threemovie.threemovieapi.Entity.VO.MovieNameDataVO
import com.threemovie.threemovieapi.Entity.VO.ShowTimeVO
import com.threemovie.threemovieapi.Repository.Support.MovieDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.TmpShowTimeRepository
import com.threemovie.threemovieapi.Utils.CalcSimilarity
import com.threemovie.threemovieapi.Utils.ChkNeedUpdate
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.regex.Matcher
import java.util.regex.Pattern

@Component
class ShowingTimeScheduler(
	val updateTimeRepositorySupport: UpdateTimeRepositorySupport,
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
	val tmpShowTimeRepository: TmpShowTimeRepository,
	val movieDataRepositorySupport: MovieDataRepositorySupport,
) {
	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val CGVurl = "http://www.cgv.co.kr"
	val LCurl = "http://www.lottecinema.co.kr"
	val mburl = "https://www.megabox.co.kr"
	val nameMap = HashMap<String, MovieNameDataVO>()
	lateinit var movieNameInfo: List<MovieNameData>
	val square = "\\[[^)]*\\]".toRegex()
	
	@Async
	@Scheduled(cron = "0 0/10 * * * ?")
	fun ChkMovieShowingTime() {
		if (ChkNeedUpdate.chkUpdateTenMinute(updateTimeRepositorySupport.getShowTime())) {
			tmpShowTimeRepository.truncateTmpShowTime()
			movieNameInfo = movieDataRepositorySupport.getMovieNameData()
			
			val mbTheaters = theaterDataRepositorySupport.getTheaterData("MB")
			val lcTheaters = theaterDataRepositorySupport.getTheaterData("LC")
			val cgvTheaters = theaterDataRepositorySupport.getTheaterData("CGV")
			
			var showTimeList = updateMBShowtimes(mbTheaters)
			showTimeList += updateLCShowtimes(lcTheaters)
			showTimeList += updateCGVShowtimes(cgvTheaters)
			
			
			tmpShowTimeRepository.saveAll(showTimeList)
			tmpShowTimeRepository.chgShowTimeTable()
			tmpShowTimeRepository.truncateTmpShowTime()
			updateTimeRepositorySupport.updateShowTime(ChkNeedUpdate.retFormatterTime())
		}
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
	
	fun updateMBShowtimes(
		theaters: List<TheaterData>
	): List<TmpShowTime> {
		var showTimeList = ArrayList<TmpShowTime>()
		var showTimeBranch = ShowTimeBranchDTO("MB")
		val url = mburl + "/on/oh/ohc/Brch/schedulePage.do"
		for (theater in theaters) {
			val brchKR = theater.brchKR
			val brchEN = theater.brchEN
			val city = theater.city
			
			showTimeBranch.brchKR = brchKR
			showTimeBranch.brchEN = brchEN
			showTimeBranch.city = city
			
			val brchNo = theater.theaterCode
			
			val dates = getMBDates(brchNo, brchKR)
			
			for (date in dates) {
				val datestr = chgStrtoDatestr(date)
				showTimeBranch.date = datestr
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
				
				val dtos = HashMap<Triple<String, String, String>, ShowTimeVO>()
				for (i in 0 until showtimes.length()) {
					val showtime = showtimes.getJSONObject(i)
					val totalSeat = showtime.getInt("totSeatCnt")
					val playSchldNo = showtime.getString("playSchdlNo")
					val movieKR = showtime.getString("rpstMovieNm")
					val playKind = showtime.getString("playKindNm")
					val screenKR = showtime.getString("theabExpoNm")
					val screenEN = showtime.getString("theabEngNm")
					val ticketPage = "https://www.megabox.co.kr/bookingByPlaySchdlNo?playSchdlNo=${playSchldNo}"
					
					val restSeat = showtime.getInt("restSeatCnt").toString()
					val startTime = showtime.getString("playStartTime")
					val endTime = showtime.getString("playEndTime")
					
					val showTimeKey = Triple(movieKR, screenKR, playKind)
					val item = HashMap<String, String>()
					item["StartTime"] = startTime
					item["EndTime"] = endTime
					item["RestSeat"] = restSeat
					item["TicketPage"] = ticketPage
					if (dtos[showTimeKey] == null) {
						val items = ArrayList<HashMap<String, String>>()
						items.add(item)
						
						val showTimeVO =
							ShowTimeVO(
								screenEN,
								totalSeat,
								items
							)
						dtos[showTimeKey] = showTimeVO
					} else {
						dtos[showTimeKey]?.items?.add(item)
					}
					
				}
				
				val list = retShowTimeList(dtos, showTimeBranch)
				showTimeList += (list)
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
	
	fun updateLCShowtimes(
		theaters: List<TheaterData>
	): List<TmpShowTime> {
		var showTimeBranch = ShowTimeBranchDTO("LC")
		var showTimeList = ArrayList<TmpShowTime>()
		for (theater in theaters) {
			val brchKR = theater.brchKR
			val brchEN = theater.brchEN
			val city = theater.city
			val cinemaCode = theater.theaterCode
			val datelist = getLCDates(cinemaCode)
			
			showTimeBranch.city = city
			showTimeBranch.brchKR = brchKR
			showTimeBranch.brchEN = brchEN
			for (date in datelist) {
				showTimeBranch.date = date
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
				
				val dtos = HashMap<Triple<String, String, String>, ShowTimeVO>()
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
					val restSeat = playdata.getInt("BookingSeatCount").toString()
					val translationCode = playdata.getInt("TranslationDivisionCode")
					
					var playKind = playdata.getString("FilmNameKR")
					if (translationCode != 900)
						playKind + "(${translation[translationCode]})"
					
					val ticketPage = getLCTicketAddr(playdata)
					
					val showTimeKey = Triple(movieKR, screenKR, playKind)
					val item = HashMap<String, String>()
					item["StartTime"] = startTime
					item["EndTime"] = endTime
					item["RestSeat"] = restSeat
					item["TicketPage"] = ticketPage
					
					if (dtos[showTimeKey] == null) {
						val items = ArrayList<HashMap<String, String>>()
						items.add(item)
						
						val showTimeVO =
							ShowTimeVO(
								screenEN,
								totalSeat,
								items
							)
						dtos[showTimeKey] = showTimeVO
					} else {
						dtos[showTimeKey]?.items?.add(item)
					}
					
				}
				
				val list = retShowTimeList(dtos, showTimeBranch)
				showTimeList += list
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
	
	fun chgStrtoDatestr(str: String): String {
		return "${str.substring(0..3)}-${str.substring(4..5)}-${str.substring(6..7)}"
	}
	
	fun chgScreenKRtoEN(screenKR: String): String {
		var screenEN: String = screenKR
		if ("관" in screenKR)
			screenEN = "CINEMA ${screenEN.replace("관", "")}"
		
		return screenEN
	}
	
	fun updateCGVShowtimes(
		theaters: List<TheaterData>
	): List<TmpShowTime> {
		var showTimeBranch = ShowTimeBranchDTO("CGV")
		var showTimeList = ArrayList<TmpShowTime>()
		for (theater in theaters) {
			val theaterCode = theater.theaterCode
			val brchKR = theater.brchKR
			val brchEN = theater.brchEN
			val city = theater.city
			val datelist = getCGVDates(theaterCode)
			
			showTimeBranch.city = city
			showTimeBranch.brchKR = brchKR
			showTimeBranch.brchEN = brchEN
			for (date in datelist) {
				val datestr = chgStrtoDatestr(date)
				showTimeBranch.date = datestr
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
				
				val dtos = HashMap<Triple<String, String, String>, ShowTimeVO>()
				for (showtime in showtimes) {
					val infoMovie = showtime.getElementsByClass("info-movie")[0]
					val movieKR = infoMovie.getElementsByTag("a")[0].text()
					
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
							val restSeat = datas[0].attr("data-seatremaincnt")
							
							val showTimeKey = Triple(movieKR, screenKR, playKind)
							val item = HashMap<String, String>()
							item["StartTime"] = startTime
							item["EndTime"] = endTime
							item["RestSeat"] = restSeat
							item["TicketPage"] = ticketPage
							
							if (dtos[showTimeKey] == null) {
								val items = ArrayList<HashMap<String, String>>()
								items.add(item)
								
								val showTimeVO =
									ShowTimeVO(
										screenEN,
										totalSeat,
										items
									)
								dtos[showTimeKey] = showTimeVO
							} else {
								dtos[showTimeKey]?.items?.add(item)
							}
							
						}
						
					}
				}
				
				val list = retShowTimeList(dtos, showTimeBranch)
				showTimeList += list
			}
		}
		return showTimeList
	}
	
	fun retShowTimeList(
		dtos: Map<Triple<String, String, String>, ShowTimeVO>,
		showTimeBranch: ShowTimeBranchDTO
	): List<TmpShowTime> {
		val ret = ArrayList<TmpShowTime>()
		
		val (movieTheater, city, brchKR, brchEN, date) = showTimeBranch
		for (dto in dtos) {
			var (movieKR, screenKR, playKind) = dto.key
			val (screenEN, totalSeat, items) = dto.value
			movieKR = movieKR.replace(square, "").trim()
			
			if (nameMap[movieKR] == null) {
				var movieName = MovieNameDataVO()
				for (nameInfo in movieNameInfo) {
					val similarity = CalcSimilarity.calcSimilarity(nameInfo.nameKR, movieKR)
					
					if (similarity > 0.7 && movieName.similarity < similarity) {
						movieName.movieId = nameInfo.movieId
						movieName.nameKR = nameInfo.nameKR.toString()
						movieName.nameEN = nameInfo.nameEN
						movieName.similarity = similarity
					}
				}
				nameMap[movieKR] = movieName
			}
			var name = nameMap[movieKR]
			
			val showTime = TmpShowTime(
				name?.movieId ?: movieKR,
				movieTheater,
				city,
				brchKR,
				brchEN,
				name?.nameKR ?: movieKR,
				name?.nameEN ?: movieKR,
				screenKR,
				screenEN,
				date,
				totalSeat,
				playKind,
				JSONArray(items).toString()
			)
			
			ret.add(showTime)
		}
		
		return ret
	}
}

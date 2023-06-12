package com.threemovie.threemovieapi.domain.theater.scheduler

import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import com.threemovie.threemovieapi.domain.theater.repository.TheaterDataRepository
import com.threemovie.threemovieapi.global.entity.LastUpdateTime
import com.threemovie.threemovieapi.global.repository.LastUpdateTimeRepository
import com.threemovie.threemovieapi.global.repository.support.LastUpdateTimeRepositorySupport
import com.threemovie.threemovieapi.global.service.ChkNeedUpdate
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.regex.Matcher
import java.util.regex.Pattern

@Component
class TheaterScheduler(
	val lastUpdateTimeRepositorySupport: LastUpdateTimeRepositorySupport,
	val lastUpdateTimeRepository: LastUpdateTimeRepository,
	val theaterDataRepository: TheaterDataRepository,
) {
	val userAgent: String =
		"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36"
	val CGVurl = "http://www.cgv.co.kr"
	val LCurl = "http://www.lottecinema.co.kr"
	val mburl = "https://www.megabox.co.kr"
	private val code = "theater"
	
	@Async
	@Scheduled(cron = "0 0/5 * * * ?")
	fun updateTheaterData() {
		var time = lastUpdateTimeRepositorySupport.getLastTime(code)
		if (time == null) {
			lastUpdateTimeRepository.save(LastUpdateTime(code, 202302110107))
			time = 202302110107
		}
		if (ChkNeedUpdate.chkUpdateTwelveHours(time)) {
			var cgv = getCGVTheaters()
			var mb = getMBTheaters()
			var lc = getLCTheaters()
			
			theaterDataRepository.saveAll(cgv)
			theaterDataRepository.saveAll(mb)
			theaterDataRepository.saveAll(lc)
			lastUpdateTimeRepositorySupport.updateLastTime(ChkNeedUpdate.retFormatterTime(), code)
		}
	}
	
	fun getMBAddr(brchNo: String, brchName: String): Triple<String, String, String> {
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
		
		val megamap = JSONObject(doc).getJSONObject("megaMap")
		val theaterinfo = megamap.getJSONObject("brchInfo")
		
		val addr = theaterinfo.getString("roadNmAddr").trim()
		val addrEN = theaterinfo.getString("engAddr").trim()
		val brchEN = theaterinfo.getString("brchEngNm")
		
		return Triple(addr, addrEN, brchEN)
	}
	
	fun getMBTheaters(): ArrayList<TheaterData> {
		val theaterlist = ArrayList<TheaterData>()
		val url = mburl + "/theater/list"
		
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
		val doc = conn.get()
		
		val test = doc.getElementsByClass("tit").text()
		
		if ("점검" in test)
			return theaterlist
		
		val cities = doc.getElementsByClass("theater-place")[0].getElementsByTag("button")
		val theaters = doc.getElementsByClass("theater-list")
		
		for (i in 0 until cities.size) {
			val theaterData = theaters[i].getElementsByTag("li")
			
			for (theater in theaterData) {
				val brchNo = theater.attr("data-brch-no")
				val brchKR = theater.text()
				val (addr, addrEN, brchEN) = getMBAddr(brchNo, brchKR)
				val city = addr.substring(0..1)
				
				val theaterData = TheaterData("MB", city, brchKR, brchEN, addr, addrEN, brchNo)
				theaterlist.add(theaterData)
			}
		}
		
		return theaterlist
	}
	
	fun getCGVBrchsEN(): HashMap<String, String> {
		val brchsEN = HashMap<String, String>()
		
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
		
		if (theatersData.isEmpty())
			return brchsEN
		
		val jsonArray = JSONArray(theatersData)
		
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
	
	fun getCGVAddress(theatercode: String): String {
		val url: String =
			"https://m.cgv.co.kr/WebApp/TheaterV4/TheaterDetail.aspx?tc=${theatercode}"
		val conn = Jsoup.connect(url)
			.userAgent(userAgent)
		val doc = conn.get()
		val addr = doc.getElementsByClass("info_map")[0].getElementsByClass("line")[0].text()
		
		return addr.trim()
	}
	
	fun getCGVTheaters(): ArrayList<TheaterData> {
		val theaterlist = ArrayList<TheaterData>()
		val brchsEN = getCGVBrchsEN()
		if (brchsEN.isEmpty())
			return theaterlist
		
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
		
		for (i in 0 until jsonArray.length()) {
			val theaters = jsonArray.getJSONObject(i)
			
			val areaTheaterList = theaters.optJSONArray("AreaTheaterDetailList")
			
			for (j in 0 until areaTheaterList.length()) {
				val theaterJSON = areaTheaterList.getJSONObject(j)
				val theaterCode = theaterJSON.getString("TheaterCode")
				val brchKR = theaterJSON.getString("TheaterName").replace("CGV", "")
				val brchEN = brchsEN[theaterCode]?.replace("CGV ", "") ?: ""
				val addr = getCGVAddress(theaterCode).trim()
				val city = addr.substring(0..1)
				
				if ("CINE de CHEF" in brchKR)
					continue
				
				val theaterData = TheaterData("CGV", city, brchKR, brchEN, addr, null, theaterCode)
				
				theaterlist.add(theaterData)
			}
		}
		
		return theaterlist
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
		
		val addr = data.getJSONObject("CinemaDetail").getString("Address")
		
		return addr
	}
	
	fun getLCTheaters(): ArrayList<TheaterData> {
		val theaterlist = ArrayList<TheaterData>()
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
			val cinemaCode: String =
				"${divisionCode}|${detailDivisionCode}|${cinemaID}"
			
			if (divisionCode == "2")
				continue
			
			var addr = getLCAddr(divisionCode, detailDivisionCode, cinemaID)
			if (brchKR == "춘천")
				addr = "강원도 춘천시 중앙로67번길 18 (죽림동)"
			val city = addr.substring(0..1)
			
			val theaterData = TheaterData("LC", city, brchKR, brchEN, addr, null, cinemaCode)
			
			theaterlist.add(theaterData)
		}
		
		return theaterlist
	}
}

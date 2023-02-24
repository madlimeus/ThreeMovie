package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.service.ShowTimeService
import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val updateTimeRepositorySupport: UpdateTimeRepositorySupport,
	val showTimeRepositorySupport: ShowTimeRepositorySupport
) : ShowTimeService {
	override fun getShowTime(movieTheater: String): List<ShowTime> {
		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()

//		if (movieTheater == "CGV" || movieTheater == "MegaBOX" || movieTheater == "LotteCinema")
//			getShowingTime(movieTheater)
//		else
//			throw NoSuchElementException("Could not find a $movieTheater")
//
//		return showTimeRepositorySupport.getShowTime(movieTheater)
		val url = "http://www.cgv.co.kr/theaters/?areacode=01&theaterCode=0046"
		val conn = Jsoup.connect(url)
		val doc = conn.get()

		

		println()

		return emptyList()
	}

	override fun getShowTimeAll(): List<ShowTime> {
		val movingShowingTime = updateTimeRepositorySupport.getMovieShowingTime()

		return showTimeRepositorySupport.getShowTimeAll()
	}

	fun getShowingTime(movieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(movieTheater)
}

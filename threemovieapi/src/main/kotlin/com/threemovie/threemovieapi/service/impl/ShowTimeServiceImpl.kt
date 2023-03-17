package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Repository.ShowTimeRepository
import com.threemovie.threemovieapi.Repository.Support.MovieInfoRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.Repository.UpdateTimeRepository
import com.threemovie.threemovieapi.service.ShowTimeService
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val showTimeRepositorySupport: ShowTimeRepositorySupport,
	val movieInfoRepositorySupport: MovieInfoRepositorySupport,
	val showTimeRepository: ShowTimeRepository,
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
	val updateTimeRepository: UpdateTimeRepository,
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


		return emptyList()
	}


	fun getShowingTime(movieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(movieTheater)
}

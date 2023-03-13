package com.threemovie.threemovieapi.Utils.Scheduler

import com.threemovie.threemovieapi.Repository.ShowTimeRepository
import com.threemovie.threemovieapi.Repository.Support.TheaterDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.UpdateTimeRepository
import org.springframework.stereotype.Component

@Component
class ShowingTimeScheduler(
	val updateTimeRepository: UpdateTimeRepository,
	val updateTimeRepositorySupport: UpdateTimeRepositorySupport,
	val showTimeRepository: ShowTimeRepository,
	val theaterDataRepositorySupport: TheaterDataRepositorySupport,
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
}

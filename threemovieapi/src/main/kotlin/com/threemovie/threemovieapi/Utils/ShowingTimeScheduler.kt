package com.threemovie.threemovieapi.Utils

import com.threemovie.threemovieapi.Repository.ShowTimeRepository
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Repository.UpdateTimeRepository
import org.springframework.stereotype.Component

@Component
class ShowingTimeScheduler(
	val updateTimeRepository: UpdateTimeRepository,
	val updateTimeRepositorySupport: UpdateTimeRepositorySupport,
	val showTimeRepository: ShowTimeRepository,
	val showTimeRepositorySupport: ShowTimeRepositorySupport
) {

//	@Async
//	@Scheduled(cron = "0/60 * * * * ?")
//	fun ChkMovieShowingTime() {
//		if (ChkNeedUpdate.chkMovieShowingTime(updateTimeRepositorySupport.getMovieShowingTime().toLong())) {
//			updateCGVShowingTime()
//			updateMegaBOXShowingTime()
//			updateLotteCinemaShowingTime()
//		}
//	}

	fun updateCGVShowingTime() {

	}

	fun updateMegaBOXShowingTime() {

	}

	fun updateLotteCinemaShowingTime() {

	}
}

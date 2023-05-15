package com.threemovie.threemovieapi.Utils.Scheduler

import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Service.MovieDataControlService
import com.threemovie.threemovieapi.Utils.ChkNeedUpdate
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MovieDataScheduler(
	val movieDataControlService: MovieDataControlService,
	val UpdateTimeRepositorySupport: UpdateTimeRepositorySupport
) {
	@Async
	@Scheduled(cron = "0 0 0/1 * * *")
	fun getMovieDataFromDaum() {
		if (ChkNeedUpdate.chkUpdateTwelveHours(UpdateTimeRepositorySupport.getMovieData())) {
			movieDataControlService.truncateAllMovieData()
			movieDataControlService.GET_MOVIE_DATA_DAUM()
			movieDataControlService.GET_MOVIE_DATA_DAUM_for_upcoming()
			UpdateTimeRepositorySupport.updateMovieData(ChkNeedUpdate.retFormatterTime())
		}
	}
}

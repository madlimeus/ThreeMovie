package com.threemovie.threemovieapi.domain.movie.scheduler

import com.threemovie.threemovieapi.domain.movie.service.MovieDataControlService
import com.threemovie.threemovieapi.global.repository.support.LastUpdateTimeRepositorySupport
import com.threemovie.threemovieapi.global.service.ChkNeedUpdate
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MovieDataScheduler(
	val movieDataControlService: MovieDataControlService,
	val LastUpdateTimeRepositorySupport: LastUpdateTimeRepositorySupport
) {
	@Async
	@Scheduled(cron = "0 0 0/1 * * *")
	fun getMovieDataFromDaum() {
		if (ChkNeedUpdate.chkUpdateTwelveHours(LastUpdateTimeRepositorySupport.getLastMovie())) {
			movieDataControlService.truncateAllMovieData()
			movieDataControlService.GET_MOVIE_DATA_DAUM()
			movieDataControlService.GET_MOVIE_DATA_DAUM_for_upcoming()
			LastUpdateTimeRepositorySupport.updateLastMovie(ChkNeedUpdate.retFormatterTime())
		}
	}
}

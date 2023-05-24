package com.threemovie.threemovieapi.domain.movie.scheduler

import com.threemovie.threemovieapi.domain.movie.service.MovieDataControlService
import com.threemovie.threemovieapi.global.entity.LastUpdateTime
import com.threemovie.threemovieapi.global.repository.LastUpdateTimeRepository
import com.threemovie.threemovieapi.global.repository.support.LastUpdateTimeRepositorySupport
import com.threemovie.threemovieapi.global.service.ChkNeedUpdate
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MovieDataScheduler(
	val movieDataControlService: MovieDataControlService,
	val lastUpdateTimeRepository: LastUpdateTimeRepository,
	val lastUpdateTimeRepositorySupport: LastUpdateTimeRepositorySupport
) {
	private val code = "movie"
	
	@Async
	@Scheduled(cron = "0 0 0/1 * * *")
	fun getMovieDataFromDaum() {
		var time = lastUpdateTimeRepositorySupport.getLastTime(code)
		if (time == null) {
			lastUpdateTimeRepository.save(LastUpdateTime(code, 202302110107))
			time = 202302110107
		}
		
		if (ChkNeedUpdate.chkUpdateTwelveHours(time)) {
			movieDataControlService.truncateAllMovieData()
			movieDataControlService.GET_MOVIE_DATA_DAUM()
			movieDataControlService.GET_MOVIE_DATA_DAUM_for_upcoming()
			lastUpdateTimeRepositorySupport.updateLastTime(ChkNeedUpdate.retFormatterTime(), code)
		}
	}
}

package com.threemovie.threemovieapi.domain.movie.scheduler

import com.threemovie.threemovieapi.domain.movie.repository.MovieReviewRepository
import com.threemovie.threemovieapi.domain.movie.service.MovieReviewService
import com.threemovie.threemovieapi.global.entity.LastUpdateTime
import com.threemovie.threemovieapi.global.repository.LastUpdateTimeRepository
import com.threemovie.threemovieapi.global.repository.support.LastUpdateTimeRepositorySupport
import com.threemovie.threemovieapi.global.service.ChkNeedUpdate
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class GetReviewFromTheater(
	val movieReviewRepository: MovieReviewRepository,
	val lastUpdateTimeRepositorySupport: LastUpdateTimeRepositorySupport,
	val movieReviewService: MovieReviewService,
	val lastUpdateTimeRepository: LastUpdateTimeRepository
) {
	private val code = "review"
	
	@Async
	@Scheduled(cron = "0 0/5 * * * ?")
	fun getReview() {
		var time = lastUpdateTimeRepositorySupport.getLastTime(code)
		if (time == null) {
			lastUpdateTimeRepository.save(LastUpdateTime(code, 202302110107))
			time = 202302110107
		}
		
		if (ChkNeedUpdate.chkUpdateTenMinute(time)) {
			movieReviewService.saveReviewData()
			lastUpdateTimeRepositorySupport.updateLastTime(ChkNeedUpdate.retFormatterTime(), code)

		}
	}
	
}

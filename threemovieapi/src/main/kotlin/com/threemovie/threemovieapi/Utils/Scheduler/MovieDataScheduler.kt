package com.threemovie.threemovieapi.Utils.Scheduler

import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import com.threemovie.threemovieapi.Service.impl.MovieDataControlServiceimpl
import com.threemovie.threemovieapi.Utils.ChkNeedUpdate
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MovieDataScheduler(
    val movieDataControlService: MovieDataControlServiceimpl,
    val UpdateTimeRepositorySupport: UpdateTimeRepositorySupport
) {
    @Async
    @Scheduled(cron = "0 0 0/12 * * *")
    fun getMovieDataFromDaum() {
        if (ChkNeedUpdate.chkUpdateTwelveHours(UpdateTimeRepositorySupport.getMovieData())) {
            movieDataControlService.truncateAllMovieData()
            movieDataControlService.GET_MOVIE_INFO_DAUM()
            movieDataControlService.GET_MOVIE_INFO_DAUM_for_upcoming()
            UpdateTimeRepositorySupport.updateMovieData(ChkNeedUpdate.retFormatterTime())
        }
    }
}
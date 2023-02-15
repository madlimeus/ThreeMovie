package com.threemovie.threemovieapi.Utils

import com.threemovie.threemovieapi.Repository.Support.UpdateTimeRepositorySupport
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChkNeedUpdate{
	companion object {
		val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")

		fun chkMovieAudience(MovieAudienceTime: Long): Boolean{
			val current = LocalDateTime.now()
			val formatted = current.format(formatter).toLong()

			return MovieAudienceTime - formatted >= 5
		}

		fun chkReviewTime(ReviewTime: Long): Boolean{
			val current = LocalDateTime.now()
			val formatted = current.format(formatter).toLong()

			return ReviewTime - formatted >= 1
		}

		fun chkMovieShowingTime(MovieShowingTime: Long): Boolean{
			val current = LocalDateTime.now()
			val formatted = current.format(formatter).toLong()

			return MovieShowingTime - formatted >= 1200
		}
	}
}

package com.threemovie.threemovieapi.Utils

import java.time.Duration.between
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChkNeedUpdate {
	companion object {
		val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")

		fun retFormatterTime(): Long {
			return LocalDateTime.now().format(formatter).toLong()
		}

		fun chkUpdateReviewTime(reviewTime: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(reviewTime.toString(), formatter)

			return between(current, formatted).seconds >= 60
		}

		fun chkUpdateShowTime(showTime: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(showTime.toString(), formatter)

			return between(current, formatted).seconds >= 60 * 10
		}

		fun chkUpdateTheaterData(theaterData: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(theaterData.toString(), formatter)

			return between(current, formatted).seconds >= 60 * 60 * 12
		}
	}
}

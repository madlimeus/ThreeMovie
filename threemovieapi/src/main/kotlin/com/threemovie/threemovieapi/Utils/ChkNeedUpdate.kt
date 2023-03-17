package com.threemovie.threemovieapi.Utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChkNeedUpdate {
	companion object {
		val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")

		fun retFormatterTime(): Long {
			return LocalDateTime.now().format(formatter).toLong()
		}

		fun chkUpdateReviewTime(ReviewTime: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = current.format(formatter).toLong()

			return formatted - ReviewTime >= 1
		}

		fun chkUpdateShowTime(ShowTime: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = current.format(formatter).toLong()

			return formatted - ShowTime >= 10
		}

		fun chkUpdateTheaterData(TheaterData: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = current.format(formatter).toLong()

			return formatted - TheaterData >= 1200
		}
	}
}

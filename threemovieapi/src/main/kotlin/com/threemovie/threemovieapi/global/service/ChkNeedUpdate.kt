package com.threemovie.threemovieapi.global.service

import java.time.Duration.between
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChkNeedUpdate {
	companion object {
		private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
		
		fun retFormatterTime(): Long {
			return LocalDateTime.now().format(formatter).toLong()
		}
		
		fun chkUpdateOneMinute(time: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(time.toString(), formatter)
			
			return between(formatted, current).seconds >= 60
		}
		
		fun chkUpdateTenMinute(time: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(time.toString(), formatter)
			
			return between(formatted, current).seconds >= 60 * 10
		}
		
		fun chkUpdateOneHour(time: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(time.toString(), formatter)
			
			return between(formatted, current).seconds >= 60 * 60 * 1
		}
		
		fun chkUpdateTwelveHours(time: Long): Boolean {
			val current = LocalDateTime.now()
			val formatted = LocalDateTime.parse(time.toString(), formatter)
			
			return between(formatted, current).seconds >= 60 * 60 * 12
		}
	}
}

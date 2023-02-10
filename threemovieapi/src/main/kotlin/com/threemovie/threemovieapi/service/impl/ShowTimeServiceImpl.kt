package com.threemovie.threemovieapi.service.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QShowTime.showTime
import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.service.ShowTimeService
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val query: JPAQueryFactory
): ShowTimeService {
	override fun getCGV(): List<ShowTime> {
		val CGVSchedules = query
			.selectFrom(showTime)
			.where(showTime.MovieTheater.eq("CGV"))
			.fetch()

		return CGVSchedules
	}
}

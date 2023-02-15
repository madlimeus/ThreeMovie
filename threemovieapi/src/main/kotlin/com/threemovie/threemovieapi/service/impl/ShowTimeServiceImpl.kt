package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Repository.ShowTimeRepository
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QShowTime.showTime
import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.service.ShowTimeService
import org.springframework.stereotype.Service

@Service
class ShowTimeServiceImpl(
	val showTimeRepository: ShowTimeRepository,
	val showTimeRepositorySupport: ShowTimeRepositorySupport
): ShowTimeService {
	override fun getShowTime(MovieTheater: String): List<ShowTime> = showTimeRepositorySupport.getShowTime(MovieTheater)

	override fun getShowTimeAll(): List<ShowTime> = showTimeRepositorySupport.getShowTimeAll()
}

package com.threemovie.threemovieapi.domain.theater.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.theater.entity.domain.QTheaterData
import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TheaterDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(TheaterData::class.java) {
	val theater: QTheaterData = QTheaterData.theaterData
	fun getTheaterDataAll(): List<TheaterData>? = query
		.selectFrom(theater)
		.fetch()
	
	fun getTheaterData(movieTheater: String): List<TheaterData> = query
		.selectFrom(theater)
		.where(theater.movieTheater.eq(movieTheater))
		.fetch()
	
}

package com.threemovie.threemovieapi.domain.theater.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QTheaterData
import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TheaterDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(TheaterData::class.java) {
	fun getTheaterDataAll(): List<TheaterData>? = query
		.selectFrom(QTheaterData.theaterData)
		.fetch()
	
	fun getTheaterData(movieTheater: String): List<TheaterData> = query
		.selectFrom(QTheaterData.theaterData)
		.where(QTheaterData.theaterData.movieTheater.eq(movieTheater))
		.fetch()
	
}

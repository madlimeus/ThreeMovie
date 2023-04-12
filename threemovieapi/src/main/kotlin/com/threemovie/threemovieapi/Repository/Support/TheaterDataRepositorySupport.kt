package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QTheaterData
import com.threemovie.threemovieapi.Entity.TheaterData
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TheaterDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(TheaterData::class.java) {
	fun getTheaterDataAll(): List<TheaterData> = query
		.selectFrom(QTheaterData.theaterData)
		.fetch()
	
	fun getTheaterData(movieTheater: String): List<TheaterData> = query
		.selectFrom(QTheaterData.theaterData)
		.where(QTheaterData.theaterData.movieTheater.eq(movieTheater))
		.fetch()
	
}

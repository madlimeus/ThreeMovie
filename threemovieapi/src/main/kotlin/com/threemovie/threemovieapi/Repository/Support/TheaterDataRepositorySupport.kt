package com.threemovie.threemovieapi.Repository.Support

import com.threemovie.threemovieapi.Entity.QTheaterData
import com.threemovie.threemovieapi.Entity.TheaterData
import com.threemovie.threemovieapi.config.QueryDslConfig
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TheaterDataRepositorySupport(
	val query: QueryDslConfig
) : QuerydslRepositorySupport(TheaterData::class.java) {
	fun getTheaterDataAll(): List<TheaterData> = query.jpaQueryFactory()
		.selectFrom(QTheaterData.theaterData)
		.fetch()

	fun getTheaterData(movieTheater: String): List<TheaterData> = query.jpaQueryFactory()
		.selectFrom(QTheaterData.theaterData)
		.where(QTheaterData.theaterData.movieTheater.eq(movieTheater))
		.fetch()

}

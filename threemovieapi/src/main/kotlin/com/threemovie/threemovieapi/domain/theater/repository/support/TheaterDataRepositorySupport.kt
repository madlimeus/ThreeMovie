package com.threemovie.threemovieapi.domain.theater.repository.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.theater.entity.domain.QTheaterData
import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import com.threemovie.threemovieapi.domain.theater.entity.dto.TheaterDTO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TheaterDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(TheaterData::class.java) {
	val theater: QTheaterData = QTheaterData.theaterData
	fun getTheaterAll(): List<TheaterDTO>? = query
		.select(
			Projections.fields(
				TheaterDTO::class.java,
				theater.movieTheater,
				theater.city,
				theater.brchKr,
				theater.brchEn,
				theater.addrKr,
				theater.addrEn,
			)
		)
		.from(theater)
		.fetch()
	
	fun getTheaterEntityByMT(movieTheater: String): List<TheaterData> = query
		.select(
			theater
		)
		.from(theater)
		.where(theater.movieTheater.eq(movieTheater))
		.fetch()
	
}

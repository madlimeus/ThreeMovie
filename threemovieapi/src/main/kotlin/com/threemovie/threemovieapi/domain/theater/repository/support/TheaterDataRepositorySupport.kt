package com.threemovie.threemovieapi.domain.theater.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.theater.entity.domain.QTheater
import com.threemovie.threemovieapi.domain.theater.entity.domain.Theater
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class TheaterDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(Theater::class.java) {
	val theater: QTheater = QTheater.theater
	fun getTheaterDataAll(): List<Theater>? = query
		.selectFrom(theater)
		.fetch()
	
	fun getTheaterData(movieTheater: String): List<Theater> = query
		.selectFrom(theater)
		.where(theater.movieTheater.eq(movieTheater))
		.fetch()
	
}

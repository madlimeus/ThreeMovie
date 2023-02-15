package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QShowTime
import com.threemovie.threemovieapi.Entity.ShowTime
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeRepositorySupport(
	val query: JPAQueryFactory
): QuerydslRepositorySupport(ShowTime::class.java) {

	fun getShowTime(MovieTheater: String): List<ShowTime>
	= query
		.selectFrom(QShowTime.showTime)
		.where(QShowTime.showTime.MovieTheater.eq(MovieTheater))
		.fetch()


	fun getShowTimeAll(): List<ShowTime>
	= query
		.selectFrom(QShowTime.showTime)
		.fetch()

}

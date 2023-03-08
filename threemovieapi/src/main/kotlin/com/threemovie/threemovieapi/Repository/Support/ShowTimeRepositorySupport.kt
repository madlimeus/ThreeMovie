package com.threemovie.threemovieapi.Repository.Support

import com.threemovie.threemovieapi.Entity.QShowTime
import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.config.QueryDslConfig
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeRepositorySupport(
	val query: QueryDslConfig
) : QuerydslRepositorySupport(ShowTime::class.java) {

	fun getShowTime(MovieTheater: String): List<ShowTime> = query.jpaQueryFactory()
		.selectFrom(QShowTime.showTime)
		.where(QShowTime.showTime.MovieTheater.eq(MovieTheater))
		.fetch()


	fun getShowTimeAll(): List<ShowTime> = query.jpaQueryFactory()
		.selectFrom(QShowTime.showTime)
		.fetch()

}

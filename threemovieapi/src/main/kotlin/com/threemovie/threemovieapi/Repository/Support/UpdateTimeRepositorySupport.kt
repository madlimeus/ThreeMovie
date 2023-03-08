package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUpdateTime
import com.threemovie.threemovieapi.Entity.UpdateTime
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UpdateTimeRepositorySupport(
	val query: JPAQueryFactory
): QuerydslRepositorySupport(UpdateTime::class.java) {

	fun getMovieAudience(): String
		= query
			.select(QUpdateTime.updateTime.movieAudience)
			.from(QUpdateTime.updateTime)
			.fetch()
			.toString()

	fun getReviewTime(): String
		= query
		.select(QUpdateTime.updateTime.reviewTime)
		.from(QUpdateTime.updateTime)
		.fetch()
		.toString()

	fun getMovieShowingTime(): String
			= query
		.select(QUpdateTime.updateTime.movieShowingTime)
		.from(QUpdateTime.updateTime)
		.fetch()
		.toString()
}

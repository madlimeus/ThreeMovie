package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUpdateTime
import com.threemovie.threemovieapi.Entity.UpdateTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UpdateTimeRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(UpdateTime::class.java) {
	val updateTime: QUpdateTime = QUpdateTime.updateTime
	
	fun getShowTime(): Long = query
		.select(QUpdateTime.updateTime.showTime)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L
	
	fun getReviewTime(): Long = query
		.select(QUpdateTime.updateTime.reviewTime)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L
	
	fun getTheaterData(): Long = query
		.select(QUpdateTime.updateTime.theaterData)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L
	fun getMovieData(): Long = query.jpaQueryFactory()
		.select(QUpdateTime.updateTime.movieData)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L

	@Transactional
	fun updateShowTime(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.showTime, newTime)
			.execute()
	}
	
	@Transactional
	fun updateReviewTime(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.reviewTime, newTime)
			.execute()
	}
	
	@Transactional
	fun updateTheaterData(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.theaterData, newTime)
			.execute()
	}
	@Transactional
	fun updateMovieData(newTime: Long): Unit {
		query.jpaQueryFactory()
			.update(updateTime)
			.set(updateTime.movieData, newTime)
			.execute()
	}
}

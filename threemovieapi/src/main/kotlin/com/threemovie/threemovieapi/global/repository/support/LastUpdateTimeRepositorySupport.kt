package com.threemovie.threemovieapi.global.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.global.entity.LastUpdateTime
import com.threemovie.threemovieapi.global.entity.QLastUpdateTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class LastUpdateTimeRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(LastUpdateTime::class.java) {
	val updateTime: QLastUpdateTime = QLastUpdateTime.lastUpdateTime
	
	fun getLastShowTime(): Long? = query
		.select(updateTime.time)
		.from(updateTime)
		.where(updateTime.code.eq("showtime"))
		.fetchOne()
	
	fun getLastReview(): Long? = query
		.select(updateTime.time)
		.from(updateTime)
		.where(updateTime.code.eq("review"))
		.fetchOne()
	
	fun getLastTheater(): Long? = query
		.select(updateTime.time)
		.from(updateTime)
		.where(updateTime.code.eq("theater"))
		.fetchOne()
	
	fun getLastMovie(): Long? = query
		.select(updateTime.time)
		.from(updateTime)
		.where(updateTime.code.eq("movie"))
		.fetchOne()
	
	@Transactional
	fun updateLastShowTime(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.time, newTime)
			.where(updateTime.code.eq("showtime"))
			.execute()
	}
	
	@Transactional
	fun updateLastReview(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.time, newTime)
			.where(updateTime.code.eq("review"))
			.execute()
	}
	
	@Transactional
	fun updateLastTheater(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.time, newTime)
			.where(updateTime.code.eq("theater"))
			.execute()
	}
	
	@Transactional
	fun updateLastMovie(newTime: Long): Unit {
		query
			.update(updateTime)
			.set(updateTime.time, newTime)
			.where(updateTime.code.eq("movie"))
			.execute()
	}
}

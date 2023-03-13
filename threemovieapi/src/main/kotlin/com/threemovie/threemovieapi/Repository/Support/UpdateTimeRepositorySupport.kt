package com.threemovie.threemovieapi.Repository.Support

import com.threemovie.threemovieapi.Entity.QUpdateTime
import com.threemovie.threemovieapi.Entity.UpdateTime
import com.threemovie.threemovieapi.config.QueryDslConfig
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UpdateTimeRepositorySupport(
	val query: QueryDslConfig
) : QuerydslRepositorySupport(UpdateTime::class.java) {

	fun getShowTime(): Long = query.jpaQueryFactory()
		.select(QUpdateTime.updateTime.showTime)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L

	fun getReviewTime(): Long = query.jpaQueryFactory()
		.select(QUpdateTime.updateTime.reviewTime)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L

	fun getTheaterData(): Long = query.jpaQueryFactory()
		.select(QUpdateTime.updateTime.theaterData)
		.from(QUpdateTime.updateTime)
		.fetchOne() ?: 0L

	@Transactional
	fun updateShowTime(newTime: Long): Unit {
		val updateTime = QUpdateTime.updateTime

		query.jpaQueryFactory()
			.update(updateTime)
			.set(updateTime.showTime, newTime)
			.execute()
	}

	@Transactional
	fun updateReviewTime(newTime: Long): Unit {
		val updateTime = QUpdateTime.updateTime

		query.jpaQueryFactory()
			.update(updateTime)
			.set(updateTime.reviewTime, newTime)
			.execute()
	}

	@Transactional
	fun updateTheaterData(newTime: Long): Unit {
		val updateTime = QUpdateTime.updateTime
		
		query.jpaQueryFactory()
			.update(updateTime)
			.set(updateTime.theaterData, newTime)
			.execute()
	}
}

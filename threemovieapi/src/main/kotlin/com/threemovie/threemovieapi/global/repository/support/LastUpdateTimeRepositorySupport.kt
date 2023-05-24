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
	
	fun getLastTime(code: String): Long? = query
		.select(updateTime.time)
		.from(updateTime)
		.where(updateTime.code.eq(code))
		.fetchOne()
	
	@Transactional
	fun updateLastTime(newTime: Long, code: String) {
		query
			.update(updateTime)
			.set(updateTime.time, newTime)
			.where(updateTime.code.eq(code))
			.execute()
	}
}

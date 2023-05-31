package com.threemovie.threemovieapi.domain.showtime.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.showtime.entity.domain.QShowTimeReserve
import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTimeReserve
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeReserveRepositorySupport(val query: JPAQueryFactory) :
	QuerydslRepositorySupport(ShowTimeReserve::class.java) {
	val em = entityManager
	val showTimeReserve: QShowTimeReserve = QShowTimeReserve.showTimeReserve
	
	fun deleteShowTimeReserveByTime(time: Long) {
		query.delete(showTimeReserve)
			.where(showTimeReserve.updatedAt.lt(time))
			.execute()
		
		em?.flush()
		em?.clear()
	}
}

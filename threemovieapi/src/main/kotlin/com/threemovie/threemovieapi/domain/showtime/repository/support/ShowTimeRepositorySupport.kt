package com.threemovie.threemovieapi.domain.showtime.repository.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieData.movieData
import com.threemovie.threemovieapi.domain.showtime.entity.domain.QShowTime
import com.threemovie.threemovieapi.domain.showtime.entity.domain.QShowTimeReserve.showTimeReserve
import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTime
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowDateDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowMovieDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimeItemDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimeReserveDTO
import com.threemovie.threemovieapi.domain.theater.entity.domain.QTheaterData.theaterData
import com.threemovie.threemovieapi.domain.theater.entity.dto.TheaterDTO
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(ShowTime::class.java) {
	val showTime: QShowTime = QShowTime.showTime
	val em = entityManager
	
	@Transactional
	fun deleteZeroReserveShowTime(time: Long) {
		query.delete(showTime)
			.where(showTime.updatedAt.lt(time))
			.execute()
		
		em?.flush()
		em?.clear()
	}
	
	fun getMovieList(): List<ShowMovieDTO> {
		
		return query
			.select(
				Projections.constructor(
					ShowMovieDTO::class.java,
					movieData.movieId,
					movieData.nameKr,
					movieData.nameEn,
					movieData.category,
					movieData.runningTime,
					movieData.country,
					movieData.poster,
					movieData.reservationRank
				)
			)
			.from(showTime)
			.leftJoin(showTime.movieData, movieData)
			.groupBy(movieData.movieId)
			.orderBy(
				movieData.reservationRate.desc()
			)
			.fetch()
	}
	
	fun getTheaterList(movieFilter: List<String>?, dateFilter: List<Long>?): List<TheaterDTO> {
		
		return query
			.select(
				Projections.constructor(
					TheaterDTO::class.java,
					theaterData.movieTheater,
					theaterData.city,
					theaterData.brchKr,
					theaterData.brchEn,
					theaterData.addrKr,
					theaterData.addrEn
				)
			)
			.from(showTime)
			.leftJoin(showTime.theaterData, theaterData)
			.where(movieIn(movieFilter), dateIn(dateFilter), theaterData.brchKr.isNotNull, theaterData.addrKr.isNotNull)
			.distinct()
			.groupBy(theaterData.movieTheater, theaterData.brchKr)
			.orderBy(theaterData.brchKr.asc())
			.fetch()
	}
	
	fun getDateList(movieFilter: List<String>?, theaterFilter: List<Pair<String, String>>?): List<ShowDateDTO> {
		return query
			.select(
				Projections.constructor(
					ShowDateDTO::class.java,
					showTime.showYmd,
				)
			)
			.from(showTime)
			.where(movieIn(movieFilter), theaterIn(theaterFilter))
			.orderBy(showTime.showYmd.asc())
			.distinct()
			.fetch()
	}
	
	fun getShowTimeList(
		movieFilter: List<String>?,
		theaterFilter: List<Pair<String, String>>?,
		dateFilter: List<Long>?
	): List<ShowTimeItemDTO> {
		return query
			.from(showTime)
			.leftJoin(showTime.theaterData, theaterData)
			.leftJoin(showTime.movieData, movieData)
			.leftJoin(showTime.showTimeReserve, showTimeReserve)
			.where(
				movieIn(movieFilter),
				theaterIn(theaterFilter),
				dateIn(dateFilter),
				showTimeReserve.ticketPage.isNotNull
			)
			.transform(
				groupBy(showTime.id).list(
					Projections.constructor(
						ShowTimeItemDTO::class.java,
						movieData.nameKr,
						movieData.poster,
						theaterData.movieTheater,
						theaterData.brchKr,
						theaterData.brchEn,
						showTime.showYmd,
						showTime.totalSeat,
						showTime.playKind,
						showTime.screenKr,
						showTime.screenEn,
						theaterData.addrKr,
						theaterData.addrEn,
						list(
							Projections.constructor(
								ShowTimeReserveDTO::class.java,
								showTimeReserve.startTime,
								showTimeReserve.endTime,
								showTimeReserve.restSeat,
								showTimeReserve.ticketPage
							)
						)
					)
				)
			)
	}
	
	fun dateIn(date: List<Long>?): BooleanExpression? {
		return if (date.isNullOrEmpty()) null else showTime.showYmd.`in`(date)
	}
	
	fun movieIn(movieId: List<String>?): BooleanExpression? {
		return if (movieId.isNullOrEmpty()) null else movieData.movieId.`in`(movieId)
	}
	
	fun theaterIn(theater: List<Pair<String, String>>?): BooleanBuilder? {
		val builder = BooleanBuilder()
		
		if (theater.isNullOrEmpty())
			return null
		
		for (i in theater.indices) {
			builder.or(theaterData.movieTheater.eq(theater[i].first).and(theaterData.brchKr.eq(theater[i].second)))
		}
		
		return builder
	}
}

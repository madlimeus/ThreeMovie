package com.threemovie.threemovieapi.domain.showtime.repository.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieData
import com.threemovie.threemovieapi.domain.showtime.entity.domain.QShowTime
import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTime
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowDateDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowMovieDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTheaterDTO
import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimeItemDTO
import com.threemovie.threemovieapi.domain.theater.entity.domain.QTheaterData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(ShowTime::class.java) {
	val movieData: QMovieData = QMovieData.movieData
	val showTime: QShowTime = QShowTime.showTime
	val theaterData: QTheaterData = QTheaterData.theaterData
	val em = entityManager
	
	@Transactional
	fun deleteZeroReserveShowTime() {
		query.delete(showTime)
			.where(showTime.showTimeReserve.isEmpty)
			.execute()
		
		em?.flush()
		em?.clear()
	}
	
	fun getMovieList(): List<ShowMovieDTO> {
		
		return query
			.select(
				Projections.fields(
					ShowMovieDTO::class.java,
					showTime.movieId,
					movieData.nameKr,
					movieData.nameEn,
					movieData.category,
					movieData.runningTime,
					movieData.country,
					movieData.poster,
					movieData.reservationRank
				)
			)
			.distinct()
			.from(showTime)
			.leftJoin(movieData)
			.fetchJoin()
			.on(showTime.movieId.eq(movieData.movieId))
			.groupBy(showTime.movieId)
			.orderBy(
				movieData.reservationRate.desc()
			)
			.fetch()
	}
	
	fun getTheaterList(movieFilter: List<String>?, dateFilter: List<Int>?): List<ShowTheaterDTO> {
		
		return query
			.select(
				Projections.fields(
					ShowTheaterDTO::class.java,
					theaterData.movieTheater,
					theaterData.brchKr,
					theaterData.brchEn,
					theaterData.city,
					theaterData.addrKr,
					theaterData.addrEn
				)
			)
			.from(showTime)
			.leftJoin(theaterData)
			.fetchJoin()
			.where(movieIn(movieFilter), dateIn(dateFilter))
			.distinct()
			.orderBy(theaterData.brchKr.asc())
			.fetch()
	}
	
	fun getDateList(movieFilter: List<String>?, theaterFilter: List<Pair<String, String>>?): List<ShowDateDTO> {
		return query
			.select(
				Projections.fields(
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
		dateFilter: List<Int>?
	): List<ShowTimeItemDTO> {
		return query
			.select(
				Projections.fields(
					ShowTimeItemDTO::class.java,
					movieData.nameKr,
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
					showTime.showTimeReserve
				)
			)
			.from(showTime)
			.orderBy(showTime.showYmd.asc())
			.leftJoin(theaterData)
			.fetchJoin()
			.leftJoin(movieData)
			.fetchJoin()
			.where(movieIn(movieFilter), theaterIn(theaterFilter), dateIn(dateFilter))
			.distinct()
			.fetch()
	}
	
	fun dateIn(date: List<Int>?): BooleanExpression? {
		return if (date.isNullOrEmpty()) null else showTime.showYmd.`in`(date)
	}
	
	fun movieIn(movieId: List<String>?): BooleanExpression? {
		return if (movieId.isNullOrEmpty()) null else showTime.movieId.`in`(movieId)
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

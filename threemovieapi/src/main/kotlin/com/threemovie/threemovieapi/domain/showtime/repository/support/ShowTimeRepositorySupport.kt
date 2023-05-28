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
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(ShowTime::class.java) {
	val movieData: QMovieData = QMovieData.movieData
	val showTime: QShowTime = QShowTime.showTime
	val theaterData: QTheaterData = QTheaterData.theaterData
	
	fun getMovieList(): List<ShowMovieDTO> {
		
		return query
			.select(
				Projections.fields(
					ShowMovieDTO::class.java,
					showTime.movieId,
					showTime.movieKr,
					showTime.movieEn,
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
	
	fun getTheaterList(movieFilter: List<String>?, dateFilter: List<String>?): List<ShowTheaterDTO> {
		
		return query
			.select(
				Projections.fields(
					ShowTheaterDTO::class.java,
					showTime.movieTheater,
					showTime.brchKr,
					showTime.brchEn,
					theaterData.city,
					theaterData.addrKr,
					theaterData.addrEn
				)
			)
			.from(showTime)
			.leftJoin(theaterData)
			.fetchJoin()
			.where(movieIn(movieFilter), dateIn(dateFilter))
			.on(showTime.movieTheater.eq(theaterData.movieTheater), showTime.brchKr.eq(theaterData.brchKr))
			.distinct()
			.orderBy(showTime.brchKr.asc())
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
		dateFilter: List<String>?
	): List<ShowTimeItemDTO> {
		return query
			.select(
				Projections.fields(
					ShowTimeItemDTO::class.java,
					showTime.movieKr,
					showTime.movieTheater,
					showTime.brchKr,
					showTime.brchEn,
					showTime.showYmd,
					showTime.totalSeat,
					showTime.playKind,
					showTime.screenKr,
					showTime.screenEn,
					theaterData.addrKr,
					theaterData.addrEn,
					showTime.items
				)
			)
			.from(showTime)
			.orderBy(showTime.showYmd.asc())
			.leftJoin(theaterData)
			.fetchJoin()
			.on(showTime.brchKr.eq(theaterData.brchKr), showTime.movieTheater.eq(theaterData.movieTheater))
			.where(movieIn(movieFilter), theaterIn(theaterFilter), dateIn(dateFilter))
			.distinct()
			.fetch()
	}
	
	fun dateIn(date: List<String>?): BooleanExpression? {
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
			builder.or(showTime.movieTheater.eq(theater[i].first).and(showTime.brchKr.eq(theater[i].second)))
		}
		
		return builder
	}
}

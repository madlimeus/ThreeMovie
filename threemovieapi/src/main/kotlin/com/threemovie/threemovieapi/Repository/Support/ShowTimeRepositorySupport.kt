package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.threemovie.threemovieapi.Entity.DTO.ShowDateDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowMovieDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTheaterDTO
import com.threemovie.threemovieapi.Entity.DTO.ShowTimeItemDTO
import com.threemovie.threemovieapi.Entity.QMovieInfo
import com.threemovie.threemovieapi.Entity.QShowTime
import com.threemovie.threemovieapi.Entity.QTheaterData
import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.config.QueryDslConfig
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ShowTimeRepositorySupport(
	val query: QueryDslConfig
) : QuerydslRepositorySupport(ShowTime::class.java) {
	val movieInfo: QMovieInfo = QMovieInfo.movieInfo
	val showTime: QShowTime = QShowTime.showTime
	val theaterInfo: QTheaterData = QTheaterData.theaterData
	
	fun getMovieList(): List<ShowMovieDTO> {
		
		return query.jpaQueryFactory()
			.select(
				Projections.fields(
					ShowMovieDTO::class.java,
					showTime.movieId,
					showTime.movieKR,
					showTime.movieEN,
					movieInfo.category,
					movieInfo.runningTime,
					movieInfo.country,
					movieInfo.poster,
					movieInfo.reservationRank
				)
			)
			.from(showTime)
			.leftJoin(movieInfo)
			.fetchJoin()
			.on(showTime.movieId.eq(movieInfo.movieId))
			.orderBy(
				movieInfo.reservationRate.desc()
			)
			.distinct()
			.fetch()
	}
	
	fun getTheaterList(movieFilter: List<String>?, dateFilter: List<String>?): List<ShowTheaterDTO> {
		
		return query.jpaQueryFactory()
			.select(
				Projections.fields(
					ShowTheaterDTO::class.java,
					showTime.movieTheater,
					showTime.brchKR,
					showTime.brchEN,
					theaterInfo.city,
					theaterInfo.addrKR,
					theaterInfo.addrEN
				)
			)
			.from(showTime)
			.leftJoin(theaterInfo)
			.fetchJoin()
			.where(movieIn(movieFilter), dateIn(dateFilter))
			.on(showTime.movieTheater.eq(theaterInfo.movieTheater), showTime.brchKR.eq(theaterInfo.brchKR))
			.distinct()
			.orderBy(showTime.brchKR.asc())
			.fetch()
	}
	
	fun getDateList(movieFilter: List<String>?, theaterFilter: List<Pair<String, String>>?): List<ShowDateDTO> {
		return query.jpaQueryFactory()
			.select(
				Projections.fields(
					ShowDateDTO::class.java,
					showTime.date,
				)
			)
			.from(showTime)
			.where(movieIn(movieFilter), theaterIn(theaterFilter))
			.orderBy(showTime.date.asc())
			.distinct()
			.fetch()
	}
	
	fun getShowTimeList(
		movieFilter: List<String>?,
		theaterFilter: List<Pair<String, String>>?,
		dateFilter: List<String>?
	): List<ShowTimeItemDTO> {
		return query.jpaQueryFactory()
			.select(
				Projections.fields(
					ShowTimeItemDTO::class.java,
					showTime.movieKR,
					showTime.movieTheater,
					showTime.brchKR,
					showTime.brchEN,
					showTime.date,
					showTime.totalSeat,
					showTime.playKind,
					showTime.screenKR,
					showTime.screenEN,
					theaterInfo.addrKR,
					theaterInfo.addrEN,
					showTime.items
				)
			)
			.from(showTime)
			.orderBy(showTime.date.asc())
			.leftJoin(theaterInfo)
			.fetchJoin()
			.on(showTime.brchKR.eq(theaterInfo.brchKR), showTime.movieTheater.eq(theaterInfo.movieTheater))
			.where(movieIn(movieFilter), theaterIn(theaterFilter), dateIn(dateFilter))
			.distinct()
			.fetch()
	}
	
	fun dateIn(date: List<String>?): BooleanExpression? {
		return if (date.isNullOrEmpty()) null else showTime.date.`in`(date)
	}
	
	fun movieIn(movieId: List<String>?): BooleanExpression? {
		return if (movieId.isNullOrEmpty()) null else showTime.movieId.`in`(movieId)
	}
	
	fun theaterIn(theater: List<Pair<String, String>>?): BooleanBuilder? {
		val builder = BooleanBuilder()
		
		if (theater.isNullOrEmpty())
			return null
		
		for (i in theater.indices) {
			builder.or(showTime.movieTheater.eq(theater[i].first).and(showTime.brchKR.eq(theater[i].second)))
		}
		
		return builder
	}
}

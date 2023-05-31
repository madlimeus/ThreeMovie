package com.threemovie.threemovieapi.domain.movie.repository.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.movie.entity.domain.*
import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieDetailDTO
import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieListDTO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MovieDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(MovieData::class.java) {
	val moviePreview: QMoviePreview = QMoviePreview.moviePreview
	val movieData: QMovieData = QMovieData.movieData
	val movieCreator: QMovieCreator = QMovieCreator.movieCreator
	val movieNameData: QMovieNameData = QMovieNameData.movieNameData
	
	fun getMovieNameData(): List<MovieNameData> =
		query
			.selectFrom(movieNameData)
			.fetch()
	
	fun getMovieList(): List<MovieListDTO>? {
		
		return query
			.select(
				Projections.fields(
					MovieListDTO::class.java,
					movieData.movieId,
					movieData.netizenAvgRate,
					movieData.reservationRate,
					movieData.nameKr,
					movieData.nameEn,
					movieData.poster,
					movieData.category,
					moviePreview.type,
					moviePreview.link
				)
			)
			.from(movieData)
			.leftJoin(moviePreview)
			.fetchJoin()
			.orderBy(
				movieData.reservationRate.desc(),
				movieData.netizenAvgRate.desc()
			)
			.limit(30)
			.fetch()
	}
	
	fun getMovieDetail(movieId: String): MovieDetailDTO? {
		
		return query
			.select(
				Projections.fields(
					MovieDetailDTO::class.java,
					movieData.movieId,
					movieData.netizenAvgRate,
					movieData.reservationRate,
					movieData.summary,
					movieData.nameKr,
					movieData.nameEn,
					movieData.makingNote,
					movieData.releaseDate,
					movieData.poster,
					movieData.category,
					moviePreview.type,
					moviePreview.link,
					movieCreator.nameKr,
					movieCreator.nameEn,
					movieCreator.roleKr,
					movieCreator.link
				)
			)
			.from(movieData)
			.where(movieData.movieId.eq(movieId))
			.join(moviePreview)
			.fetchJoin()
			.join(movieCreator)
			.fetchJoin()
			.fetchOne()
	}
}

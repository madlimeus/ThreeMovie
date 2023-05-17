package com.threemovie.threemovieapi.domain.movie.repository.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.*
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieNameData
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
					movieData.nameKR,
					movieData.nameEN,
					movieData.poster,
					movieData.category,
					moviePreview.steelcuts,
					moviePreview.trailer
				)
			)
			.from(movieData)
			.leftJoin(moviePreview)
			.fetchJoin()
			.on(movieData.movieId.eq(moviePreview.movieId))
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
					movieData.nameKR,
					movieData.nameEN,
					movieData.makingNote,
					movieData.releaseDate,
					movieData.poster,
					movieData.category,
					moviePreview.steelcuts,
					moviePreview.trailer,
					movieCreator.items
				)
			)
			.from(movieData)
			.where(movieData.movieId.eq(movieId))
			.join(moviePreview)
			.fetchJoin()
			.on(movieData.movieId.eq(moviePreview.movieId))
			.join(movieCreator)
			.fetchJoin()
			.on(movieData.movieId.eq(movieCreator.movieId))
			.fetchOne()
	}
}

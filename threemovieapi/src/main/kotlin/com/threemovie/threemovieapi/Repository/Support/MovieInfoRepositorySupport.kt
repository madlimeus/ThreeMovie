package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.*
import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MovieInfoRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(MovieInfo::class.java) {
	val moviePreview: QMoviePreview = QMoviePreview.moviePreview
	val movieInfo: QMovieInfo = QMovieInfo.movieInfo
	val movieCreator: QMovieCreator = QMovieCreator.movieCreator
	
	fun getMovieNameInfo(): List<MovieNameInfo> =
		query
			.selectFrom(QMovieNameInfo.movieNameInfo)
			.fetch()
	
	fun getMovieList(): List<MovieListDTO> {
		
		return query
			.select(
				Projections.fields(
					MovieListDTO::class.java,
					movieInfo.movieId,
					movieInfo.netizenAvgRate,
					movieInfo.reservationRate,
					movieInfo.nameKR,
					movieInfo.nameEN,
					movieInfo.poster,
					movieInfo.category,
					moviePreview.steelcuts,
					moviePreview.trailer
				)
			)
			.from(movieInfo)
			.leftJoin(moviePreview)
			.fetchJoin()
			.on(movieInfo.movieId.eq(moviePreview.movieId))
			.orderBy(
				movieInfo.reservationRate.desc(),
				movieInfo.netizenAvgRate.desc()
			)
			.fetch()
	}
	
	fun getMovieDetail(movieId: String): MovieDetailDTO {
		
		return query
			.select(
				Projections.fields(
					MovieDetailDTO::class.java,
					movieInfo.movieId,
					movieInfo.netizenAvgRate,
					movieInfo.reservationRate,
					movieInfo.summary,
					movieInfo.nameKR,
					movieInfo.nameEN,
					movieInfo.makingNote,
					movieInfo.releaseDate,
					movieInfo.poster,
					movieInfo.category,
					moviePreview.steelcuts,
					moviePreview.trailer,
					movieCreator.items
				)
			)
			.from(movieInfo)
			.where(movieInfo.movieId.eq(movieId))
			.join(moviePreview)
			.fetchJoin()
			.on(movieInfo.movieId.eq(moviePreview.movieId))
			.join(movieCreator)
			.fetchJoin()
			.on(movieInfo.movieId.eq(movieCreator.movieId))
			.fetchOne()
			?: throw NoSuchElementException()
	}
}

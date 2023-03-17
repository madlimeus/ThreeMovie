package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.core.types.Projections
import com.threemovie.threemovieapi.Entity.*
import com.threemovie.threemovieapi.Entity.DTO.MovieDetailDTO
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.config.QueryDslConfig
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MovieInfoRepositorySupport(
	val query: QueryDslConfig
) : QuerydslRepositorySupport(ShowTimeMovieInfo::class.java) {
	fun getShowTimeMovieInfo(): List<ShowTimeMovieInfo> =
		query.jpaQueryFactory()
			.selectFrom(QShowTimeMovieInfo.showTimeMovieInfo)
			.fetch()

	fun getMovieList(): List<MovieListDTO> {
		val moviePreview = QMoviePreview.moviePreview
		val movieInfo = QMovieInfo.movieInfo

		val movieList = query.jpaQueryFactory()
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
			.join(moviePreview)
			.on(movieInfo.movieId.eq(moviePreview.movieId))
			.fetch()

		return movieList
	}

	fun getMovieDetail(movieId: String): MovieDetailDTO {
		val moviePreview = QMoviePreview.moviePreview
		val movieInfo = QMovieInfo.movieInfo
		val movieCreator = QMovieCreator.movieCreator

		val movieDetail = query.jpaQueryFactory()
			.select(
				Projections.fields(
					MovieDetailDTO::class.java,
					movieInfo.movieId,
					movieInfo.netizenAvgRate,
					movieInfo.reservationRate,
					movieInfo.summary,
					movieInfo.nameKR,
					movieInfo.nameEN,
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
			.on(movieInfo.movieId.eq(moviePreview.movieId))
			.join(movieCreator)
			.on(movieInfo.movieId.eq(movieCreator.movieId))
			.fetchOne()
			?: throw NoSuchElementException()

		return movieDetail
	}
}

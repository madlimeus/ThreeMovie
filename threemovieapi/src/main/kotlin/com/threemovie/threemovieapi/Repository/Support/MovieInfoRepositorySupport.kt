package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.core.types.Projections
import com.threemovie.threemovieapi.Entity.DTO.MovieListDTO
import com.threemovie.threemovieapi.Entity.QMovieInfo
import com.threemovie.threemovieapi.Entity.QMoviePreview
import com.threemovie.threemovieapi.Entity.QShowTimeMovieInfo
import com.threemovie.threemovieapi.Entity.ShowTimeMovieInfo
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
					movieInfo.MovieId,
					movieInfo.NameKR,
					movieInfo.NameEN,
					movieInfo.Poster,
					movieInfo.Category,
					moviePreview.Steelcuts,
					moviePreview.Trailer
				)
			)
			.from(movieInfo)
			.join(moviePreview)
			.on(movieInfo.MovieId.eq(moviePreview.MovieId))
			.fetch()
		println(movieList)
		return movieList
	}
}

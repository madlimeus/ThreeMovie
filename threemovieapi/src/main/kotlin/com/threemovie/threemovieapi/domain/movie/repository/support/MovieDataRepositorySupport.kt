package com.threemovie.threemovieapi.domain.movie.repository.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieCreator.movieCreator
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMoviePreview.moviePreview
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieReview.movieReview
import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieDetailDTO
import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieListDTO
import com.threemovie.threemovieapi.domain.movie.entity.dto.MovieNameDTO
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MovieDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(MovieData::class.java) {
	val movieData: QMovieData = QMovieData.movieData
	
	fun getMovieNameData(): List<MovieNameDTO> =
		query
			.select(
				Projections.fields(
					MovieNameDTO::class.java,
					movieData.movieId,
					movieData.nameKr
				)
			)
			.from(movieData)
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
			.leftJoin(movieData.previews, moviePreview)
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
					movieCreator.link,
					movieReview
				)
			)
			.from(movieData)
			.where(movieData.movieId.eq(movieId))
			.join(movieData.previews, moviePreview)
			.fetchJoin()
			.join(movieData.creators, movieCreator)
			.fetchJoin()
			.join(movieData.reviews, movieReview)
			.fetchJoin()
			.fetchOne()
	}
}

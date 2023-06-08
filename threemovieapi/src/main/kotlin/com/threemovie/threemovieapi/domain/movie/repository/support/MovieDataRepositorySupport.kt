package com.threemovie.threemovieapi.domain.movie.repository.support

import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
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
import com.threemovie.threemovieapi.domain.movie.entity.dto.MoviePreviewDTO
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
			.orderBy(moviePreview.type.desc())
			.fetch()
	
	fun getMainMovie(): List<MovieListDTO>? {
		
		return query
			.from(movieData)
			.leftJoin(movieData.previews, moviePreview)
			.orderBy(
				movieData.reservationRate.desc(),
				movieData.netizenAvgRate.desc(),
				moviePreview.type.asc()
			)
			.transform(
				groupBy(movieData.movieId).list(
					(
							Projections.constructor(
								MovieListDTO::class.java,
								movieData.movieId,
								movieData.netizenAvgRate,
								movieData.reservationRate,
								movieData.nameKr,
								movieData.nameEn,
								movieData.poster,
								movieData.category,
								list(
									Projections.constructor(
										MoviePreviewDTO::class.java,
										moviePreview.type,
										moviePreview.link,
									)
								)
							)
							)
				)
			).subList(0, 20)
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
			.leftJoin(movieData.previews, moviePreview)
			.leftJoin(movieData.creators, movieCreator)
			.leftJoin(movieData.reviews, movieReview)
			.fetchOne()
	}
}

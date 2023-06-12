package com.threemovie.threemovieapi.domain.movie.repository.support

import com.querydsl.core.BooleanBuilder
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.set
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieCreator.movieCreator
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieData
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMoviePreview.moviePreview
import com.threemovie.threemovieapi.domain.movie.entity.domain.QMovieReview.movieReview
import com.threemovie.threemovieapi.domain.movie.entity.dto.*
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MovieDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(MovieData::class.java) {
	val movieData: QMovieData = QMovieData.movieData
	
	fun getMovieByKeyword(keyword: String?): Set<MovieSearchDTO> =
		query
			.select(
				Projections.fields(
					MovieSearchDTO::class.java,
					movieData.movieId,
					movieData.netizenAvgRate,
					movieData.reservationRate,
					movieData.nameKr,
					movieData.nameEn,
					movieData.poster
				)
			)
			.orderBy(
				movieData.reservationRate.desc(),
				movieData.netizenAvgRate.desc()
			)
			.groupBy(movieData.movieId)
			.from(movieData)
			.leftJoin(movieData.creators, movieCreator)
			.where(movieSearchBooleanBuilder(keyword), movieData.nameKr.isNotEmpty)
			.fetch().toSet()
	
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
	
	fun getMainMovie(): List<MovieMainDTO>? {
		
		return query
			.from(movieData)
			.leftJoin(movieData.previews, moviePreview)
			.orderBy(
				movieData.reservationRate.desc(),
				movieData.netizenAvgRate.desc(),
				moviePreview.type.asc()
			)
			.distinct()
			.transform(
				groupBy(movieData.movieId).list(
					(
							Projections.constructor(
								MovieMainDTO::class.java,
								movieData.movieId,
								movieData.netizenAvgRate,
								movieData.reservationRate,
								movieData.nameKr,
								movieData.nameEn,
								movieData.poster,
								movieData.category,
								set(
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
	
	fun getMovieDetail(movieId: String): List<MovieDetailDTO> {
		val ret = query
			.from(movieData)
			.where(movieData.movieId.eq(movieId))
			.leftJoin(movieData.previews, moviePreview)
			.leftJoin(movieData.creators, movieCreator)
			.leftJoin(movieData.reviews, movieReview)
			.orderBy(movieReview.recommendation.desc())
			.transform(
				groupBy(
					movieData.movieId
				).list(
					Projections.constructor(
						MovieDetailDTO::class.java,
						movieData.movieId,
						movieData.netizenAvgRate,
						movieData.reservationRate,
						movieData.runningTime,
						movieData.admissionCode,
						movieData.country,
						movieData.reservationRank,
						movieData.totalAudience,
						movieData.summary,
						movieData.makingNote,
						movieData.nameKr,
						movieData.nameEn,
						movieData.releaseDate,
						movieData.poster,
						movieData.category,
						set(
							Projections.constructor(
								MovieCreatorDTO::class.java,
								movieCreator.nameKr,
								movieCreator.nameEn,
								movieCreator.roleKr,
								movieCreator.link
							).`as`("creator")
						),
						set(
							Projections.constructor(
								MovieReviewDTO::class.java,
								movieReview.review,
								movieReview.date,
								movieReview.recommendation,
								movieReview.movieTheater
							).`as`("review")
						),
						set(
							Projections.constructor(
								MoviePreviewDTO::class.java,
								moviePreview.type,
								moviePreview.link,
							).`as`("preview")
						)
					)
				)
			)
		
		return ret
	}
	
	fun movieSearchBooleanBuilder(keyword: String?): BooleanBuilder {
		val booleanBuilder = BooleanBuilder()
		
		if (! keyword.isNullOrEmpty()) {
			booleanBuilder.or(movieNameKrContain(keyword))
			booleanBuilder.or(movieNameEnContain(keyword))
			booleanBuilder.or(movieCategoryContain(keyword))
			booleanBuilder.or(creatorNameKrContain(keyword))
			booleanBuilder.or(creatorNameEnContain(keyword))
			booleanBuilder.or(creatorRoleKrContain(keyword))
		}
		
		return booleanBuilder
	}
	
	fun movieNameKrContain(keyword: String): BooleanExpression {
		return movieData.nameKr.contains(keyword)
	}
	
	fun movieNameEnContain(keyword: String): BooleanExpression {
		return movieData.nameEn.contains(keyword)
	}
	
	fun movieCategoryContain(keyword: String): BooleanExpression {
		return movieData.category.contains(keyword)
	}
	
	fun creatorNameKrContain(keyword: String): BooleanExpression {
		return movieCreator.nameKr.contains(keyword)
	}
	
	fun creatorNameEnContain(keyword: String): BooleanExpression {
		return movieCreator.nameEn.contains(keyword)
	}
	
	fun creatorRoleKrContain(keyword: String): BooleanExpression {
		return movieCreator.roleKr.contains(keyword)
	}
}

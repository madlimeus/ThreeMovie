package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "MovieReview")
class MovieReview(
	val recommendation: String? = "",
	
	val date: String? = "",
	
	val review: String? = "",
	
	val movieTheater: String? = "",
) : PrimaryKeyEntity() {
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "movie_id",
		referencedColumnName = "movieId",
		foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
	)
	var movieData: MovieData? = null
}

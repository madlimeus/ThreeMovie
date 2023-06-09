package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLInsert

@Entity
@Table(name = "MovieReview")
@SQLInsert(
	sql = "INSERT INTO movie_review(date, movie_id, movie_theater, recommendation, review,  id)" +
			"VALUES (?, ?, ?, ?, ?, ?)" +
			"ON DUPLICATE KEY UPDATE" +
			" recommendation = VALUES(recommendation)"
)
class MovieReview(
	@NotNull
	val recommendation: Int = 0,

	@NotNull
	val date: Long = 202303030506,

	@Column(length = 500, unique = true)
	val review: String? = "",

	@NotNull
	val movieTheater: String = "",
) : PrimaryKeyEntity() {
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
	@JoinColumn(
		name = "movie_id",
		referencedColumnName = "movieId"
	)
	var movieData: MovieData? = null
}
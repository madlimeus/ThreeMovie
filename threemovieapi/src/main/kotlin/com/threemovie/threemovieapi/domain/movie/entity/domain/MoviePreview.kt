package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "MoviePreview")
class MoviePreview(
	@NotNull
	val type: String,
	
	@NotNull
	@Column(length = 500)
	val link: String,
) : PrimaryKeyEntity() {
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "movieId")
	var movieData: MovieData? = null
}

package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "MovieCreator")
class MovieCreator(
	@NotNull
	var nameKr: String,
	
	var nameEn: String?,
	
	var roleKr: String?,
	
	@NotNull
	@Column(length = 500)
	var link: String,
) : PrimaryKeyEntity() {
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "movieId")
	var movieData: MovieData? = null
}

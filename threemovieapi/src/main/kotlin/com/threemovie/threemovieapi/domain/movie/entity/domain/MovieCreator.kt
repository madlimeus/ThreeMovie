package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "MovieCreator")
class MovieCreator(
	@NotNull
	var nameKr: String,
	
	@NotNull
	var nameEn: String,
	
	@NotNull
	var roleKr: String,
	
	@NotNull
	@Column(length = 500)
	var link: String,
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_data_movie_id")
	var movieData: MovieData
) : PrimaryKeyEntity()

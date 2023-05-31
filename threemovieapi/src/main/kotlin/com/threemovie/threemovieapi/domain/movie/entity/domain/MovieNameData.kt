package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "Moviedata")
class MovieNameData(
	@NotNull
	val movieId: String = "",
	
	@NotNull
	val nameKr: String = "",
) : PrimaryKeyEntity()

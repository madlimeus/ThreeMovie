package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.repository.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "MoviePreview")
class MoviePreview(
	@NotNull
	val movieId: String = "",
	
	val steelcuts: String? = "",
	
	val trailer: String? = "",
) : PrimaryKeyEntity()

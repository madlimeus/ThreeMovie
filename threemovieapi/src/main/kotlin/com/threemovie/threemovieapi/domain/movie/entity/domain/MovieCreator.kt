package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.repository.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "MovieCreator")
class MovieCreator(
	@NotNull
	val movieId: String = "",
	
	val items: String? = "",
) : PrimaryKeyEntity()

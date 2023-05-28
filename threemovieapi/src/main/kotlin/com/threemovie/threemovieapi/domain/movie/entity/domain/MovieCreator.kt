package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "MovieCreator")
class MovieCreator(
	@NotNull
	val movieId: String = "",
	
	@Column(columnDefinition = "longtext")
	val items: String? = "",
) : PrimaryKeyEntity()

package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "MoviePreview")
class MoviePreview(
	@NotNull
	val movieId: String = "",
	
	@Column(columnDefinition = "longtext")
	val steelcuts: String? = "",
	
	@Column(columnDefinition = "longtext")
	val trailer: String? = "",
) : PrimaryKeyEntity()

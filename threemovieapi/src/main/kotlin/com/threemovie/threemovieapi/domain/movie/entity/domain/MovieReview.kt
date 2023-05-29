package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "MovieReview")
class MovieReview(
	@NotNull
	val movieId: String = "",
	
	val recommendation: String? = "",
	
	val date: String? = "",
	
	val review: String? = "",
	
	val movieTheater: String? = "",
) : PrimaryKeyEntity()

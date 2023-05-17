package com.threemovie.threemovieapi.domain.movie.entity.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MovieCreator")
data class MovieCreator(
	@Id
	@Column(name = "MovieId")
	val movieId: String = "",
	
	@Column(name = "Items")
	val items: String? = "",
)

package com.threemovie.threemovieapi.Entity

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

	@Column(name = "Director")
	val director: String = "",

	@Column(name = "Actor")
	val actor: String = "",

	@Column(name = "PhotoAddress")
	val photoAddress: String = "",
)

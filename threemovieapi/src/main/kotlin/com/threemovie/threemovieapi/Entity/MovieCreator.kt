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
	val MovieId: String = "",

	@Column(name = "Director")
	val Director: String = "",

	@Column(name = "Actor")
	val Actor: String = "",

	@Column(name = "PhotoAddress")
	val PhotoAddress: String = "",
)

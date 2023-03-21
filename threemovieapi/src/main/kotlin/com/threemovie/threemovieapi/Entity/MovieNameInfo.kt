package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MovieInfo")
data class MovieNameInfo(
	@Id
	@Column(name = "MovieId")
	val movieId: String = "",

	@Column(name = "NameKR")
	val nameKR: String? = "",

	@Column(name = "NameEN")
	val nameEN: String? = "",
)

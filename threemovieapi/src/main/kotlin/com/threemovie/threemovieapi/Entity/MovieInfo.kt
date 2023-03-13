package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MovieInfo")
data class MovieInfo(
	@Id
	@Column(name = "MovieId")
	val MovieId: String = "",

	@Column(name = "Summary")
	val Summary: String = "",

	@Column(name = "NameKR")
	val NameKR: String = "",

	@Column(name = "NameEN")
	val NameEN: String = "",

	@Column(name = "ReleaseDate")
	val ReleaseDate: String? = "",

	@Column(name = "Poster")
	val Poster: String? = "",

	@Column(name = "Category")
	val Category: String = "",
)

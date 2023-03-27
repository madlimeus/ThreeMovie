package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ShowTimes")
data class ShowTimeMovieInfo(
	@Id
	@Column(name = "MovieId")
	val movieId: String = "",

	@Column(name = "MovieKR")
	val movieKR: String = "",
)

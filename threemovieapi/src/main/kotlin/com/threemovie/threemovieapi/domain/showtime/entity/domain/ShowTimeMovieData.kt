package com.threemovie.threemovieapi.domain.showtime.entity.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ShowTimes")
data class ShowTimeMovieData(
	@Id
	@Column(name = "MovieId")
	val movieId: String = "",
	
	@Column(name = "MovieKR")
	val movieKR: String = "",
)

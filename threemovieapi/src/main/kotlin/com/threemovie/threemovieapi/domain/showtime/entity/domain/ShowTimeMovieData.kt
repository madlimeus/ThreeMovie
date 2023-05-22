package com.threemovie.threemovieapi.domain.showtime.entity.domain

import com.threemovie.threemovieapi.global.repository.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "ShowTime")
class ShowTimeMovieData(
	@Column(name = "MovieId")
	val movieId: String = "",
	
	@Column(name = "MovieKR")
	val movieKR: String = "",
) : PrimaryKeyEntity()

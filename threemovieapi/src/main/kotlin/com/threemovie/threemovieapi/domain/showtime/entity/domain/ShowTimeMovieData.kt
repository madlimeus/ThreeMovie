package com.threemovie.threemovieapi.domain.showtime.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "ShowTime")
class ShowTimeMovieData(
	val movieId: String = "",
	
	val movieKr: String = "",
) : PrimaryKeyEntity()

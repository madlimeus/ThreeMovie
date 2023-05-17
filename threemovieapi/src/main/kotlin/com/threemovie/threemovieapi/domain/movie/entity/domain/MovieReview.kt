package com.threemovie.threemovieapi.domain.movie.entity.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MovieReview")
data class MovieReview(
	
	@Column(name = "MovieId")
	val movieId: String = "",
	
	@Column(name = "Recommendation")
	val recommendation: String? = "",
	
	@Column(name = "Date")
	val date: String? = "",
	@Id
	@Column(name = "Review")
	val review: String? = "",
	
	@Column(name = "MovieTheater")
	val movieTheater: String? = "",
)

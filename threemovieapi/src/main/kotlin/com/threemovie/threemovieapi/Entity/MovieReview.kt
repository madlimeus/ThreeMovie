package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MovieReview")
data class MovieReview(
	@Id
	@Column(name = "MovieId")
	val movieId: String = "",

	@Column(name = "MovieTheater")
	val movieTheater: String = "",

	@Column(name = "Recommendation")
	val recommendation: String? = "",

	@Column(name = "Date")
	val date: String? = "",

	@Column(name = "Review")
	val review: String? = "",
)

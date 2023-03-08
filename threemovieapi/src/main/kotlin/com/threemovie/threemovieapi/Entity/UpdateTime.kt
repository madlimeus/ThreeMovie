package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UpdateTime")
data class UpdateTime(
	@Id
	@Column(name = "MovieAudience")
	var movieAudience: Long = 202302110107,

	@Column(name = "ReviewTime")
	var reviewTime: Long = 202302110107,

	@Column(name = "MovieShowingTime")
	var movieShowingTime: Long = 202302110107
)

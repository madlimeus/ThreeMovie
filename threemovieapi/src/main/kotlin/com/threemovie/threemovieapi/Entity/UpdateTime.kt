package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UpdateTime")
data class UpdateTime(
	@Column(name = "ShowTime")
	var showTime: Long = 202302110107,

	@Column(name = "ReviewTime")
	var reviewTime: Long = 202302110107,

	@Id
	@Column(name = "TheaterData")
	var theaterData: Long = 202302110107,
)

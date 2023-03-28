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
	val movieId: String = "",

	@Column(name = "NetizenAvgRate")
	val netizenAvgRate: Double? = 0.0,

	@Column(name = "ReservationRate")
	val reservationRate: Double? = 0.0,

	@Column(name = "Summary")
	val summary: String = "",

	@Column(name = "NameKR")
	val nameKR: String? = "",

	@Column(name = "NameEN")
	val nameEN: String? = null,

	@Column(name = "ReleaseDate")
	val releaseDate: String = "",

	@Column(name = "Poster")
	val poster: String? = null,

	@Column(name = "Category")
	val category: String? = null,

	@Column(name = "MakingNote")
	val makingNote: String? = null,
)

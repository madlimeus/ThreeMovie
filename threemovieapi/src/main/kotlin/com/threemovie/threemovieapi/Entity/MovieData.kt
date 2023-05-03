package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "MovieData")
data class MovieData(
	@Id
	@Column(name = "MovieId")
	val movieId: String = "",
	
	@Column(name = "NetizenAvgRate")
	val netizenAvgRate: Double? = 0.0,
	
	@Column(name = "ReservationRate")
	val reservationRate: Double? = 0.0,
	
	@Column(name = "Summary")
	val summary: String? = "",
	
	@Column(name = "NameKR")
	val nameKR: String? = "",
	
	@Column(name = "NameEN")
	val nameEN: String? = "",
	
	@Column(name = "ReleaseDate")
	val releaseDate: String = "",
	
	@Column(name = "Poster")
	val poster: String? = "",
	
	@Column(name = "Category")
	val category: String? = "",
	
	@Column(name = "MakingNote")
	val makingNote: String? = "",
	
	@Column(name = "RunningTime")
	val runningTime: String? = "",
	
	@Column(name = "AdmissionCode")
	val admissionCode: String? = "",
	
	@Column(name = "Country")
	val country: String? = "",
	
	@Column(name = "ReservationRank")
	val reservationRank: String? = "",
	
	@Column(name = "TotalAudience")
	val totalAudience: String? = "",
	
	)

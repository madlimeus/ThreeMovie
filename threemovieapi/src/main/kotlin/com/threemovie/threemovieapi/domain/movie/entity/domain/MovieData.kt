package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.repository.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "MovieData")
class MovieData(
	@NotNull
	val movieId: String = "",
	
	val netizenAvgRate: Double? = 0.0,
	
	val reservationRate: Double? = 0.0,
	
	val summary: String? = "",
	
	val nameKR: String? = "",
	
	val nameEN: String? = "",
	
	@NotNull
	val releaseDate: String = "",
	
	val poster: String? = "",
	
	val category: String? = "",
	
	val makingNote: String? = "",
	
	val runningTime: String? = "",
	
	val admissionCode: String? = "",
	
	val country: String? = "",
	
	val reservationRank: String? = "",
	
	val totalAudience: String? = "",
	
	) : PrimaryKeyEntity()

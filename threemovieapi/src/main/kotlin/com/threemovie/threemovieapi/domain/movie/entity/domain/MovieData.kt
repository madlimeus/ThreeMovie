package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
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
	
	@Column(columnDefinition = "longtext")
	val summary: String? = "",
	
	val nameKR: String? = "",
	
	val nameEN: String? = "",
	
	@NotNull
	val releaseDate: String = "",
	
	val poster: String? = "",
	
	val category: String? = "",
	
	@Column(columnDefinition = "longtext")
	val makingNote: String? = "",
	
	val runningTime: String? = "",
	
	val admissionCode: String? = "",
	
	val country: String? = "",
	
	val reservationRank: String? = "",
	
	val totalAudience: String? = "",
	
	) : PrimaryKeyEntity()

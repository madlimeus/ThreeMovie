package com.threemovie.threemovieapi.domain.showtime.entity.domain

import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimePK
import jakarta.persistence.*

@Entity
@IdClass(ShowTimePK::class)
@Table(name = "ShowTimes")
data class ShowTime(
	@Column(name = "MovieId")
	val movieId: String = "",
	
	@Id
	@Column(name = "MovieTheater")
	val movieTheater: String = "",
	
	@Column(name = "City")
	val city: String = "서울",
	
	@Id
	@Column(name = "BrchKR")
	val brchKR: String = "",
	
	@Column(name = "BrchEN")
	val brchEN: String = "",
	
	@Id
	@Column(name = "MovieKR")
	val movieKR: String = "",
	
	@Column(name = "MovieEN")
	val movieEN: String = "",
	
	@Id
	@Column(name = "ScreenKR")
	val screenKR: String = "",
	
	@Column(name = "ScreenEN")
	val screenEN: String = "",
	
	@Id
	@Column(name = "Date")
	val date: String = "",
	
	@Column(name = "TotalSeat")
	val totalSeat: Int = 200,
	
	@Id
	@Column(name = "PlayKind")
	val playKind: String = "",
	
	@Column(name = "Items", columnDefinition = "json")
	val items: String = "",
)

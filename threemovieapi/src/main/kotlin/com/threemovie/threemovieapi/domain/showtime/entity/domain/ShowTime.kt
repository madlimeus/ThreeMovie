package com.threemovie.threemovieapi.domain.showtime.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "ShowTime")
class ShowTime(
	@NotNull
	val movieId: String = "",
	
	@NotNull
	val movieTheater: String = "",
	
	@NotNull
	val city: String = "서울",
	
	@NotNull
	val brchKR: String = "",
	
	val brchEN: String? = "",
	
	@NotNull
	val movieKR: String = "",
	
	val movieEN: String? = "",
	
	@NotNull
	val screenKR: String = "",
	
	@NotNull
	val screenEN: String = "",
	
	@NotNull
	val date: String = "",
	
	@NotNull
	val totalSeat: Int = 200,
	
	@NotNull
	val playKind: String = "",
	
	@Column(name = "Items", columnDefinition = "json")
	val items: String = "",
) : PrimaryKeyEntity()

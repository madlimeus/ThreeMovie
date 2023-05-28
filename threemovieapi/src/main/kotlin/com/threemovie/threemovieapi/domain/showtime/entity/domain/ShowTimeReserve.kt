package com.threemovie.threemovieapi.domain.showtime.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "ShowTimeReserve")
class ShowTimeReserve(
	@Column(unique = true)
	@NotNull
	val ticketPage: String = "",
	
	@NotNull
	val movieTheater: String = "",
	
	@NotNull
	val city: String = "서울",
	
	@NotNull
	val brchKr: String = "",
) : PrimaryKeyEntity()

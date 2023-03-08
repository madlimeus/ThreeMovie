package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "TheaterData")
data class TheaterData(
	@Column(name = "MovieTheater")
	val movieTheater: String = "MT",

	@Column(name = "City")
	val city: String = "서울",

	@Column(name = "BrchKR")
	val brchKR: String = "",

	@Column(name = "BrchEN")
	val brchEN: String = "",

	@Column(name = "AddrKR")
	val addrKR: String = "",

	@Column(name = "AddrEN")
	val addrEN: String = "",

	@Id
	@Column(name = "TheaterCode")
	val theaterCode: String = "1234"
)

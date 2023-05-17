package com.threemovie.threemovieapi.domain.theater.entity.domain

import com.threemovie.threemovieapi.domain.theater.entity.dto.TheaterDataPK
import jakarta.persistence.*
import org.hibernate.annotations.SQLInsert

@Entity
@Table(name = "TheaterData")
@IdClass(TheaterDataPK::class)
@SQLInsert(
	sql = "INSERT IGNORE INTO TheaterData(AddrEN, AddrKR, BrchEN, BrchKR, City, MovieTheater, TheaterCode)" +
			"VALUES(?,?,?,?,?,?,?) "
)
data class TheaterData(
	@Id
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
	
	@Column(name = "AddrEN", nullable = true)
	val addrEN: String? = null,
	
	@Id
	@Column(name = "TheaterCode")
	val theaterCode: String = "1234",
)

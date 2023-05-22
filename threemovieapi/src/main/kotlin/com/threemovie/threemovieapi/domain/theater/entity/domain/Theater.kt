package com.threemovie.threemovieapi.domain.theater.entity.domain

import com.threemovie.threemovieapi.global.repository.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLInsert
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "Theater")
@SQLInsert(
	sql = "INSERT IGNORE INTO Theater(AddrEN, AddrKR, BrchEN, BrchKR, City, MovieTheater, TheaterCode)" +
			"VALUES(?,?,?,?,?,?,?) "
)
class Theater(
	@NotNull
	val movieTheater: String = "MT",
	
	@NotNull
	val city: String = "서울",
	
	@NotNull
	val brchKR: String = "",
	
	@NotNull
	val brchEN: String = "",
	
	@NotNull
	val addrKR: String = "",
	
	val addrEN: String? = null,
	
	@NotNull
	val theaterCode: String = "1234",
) : PrimaryKeyEntity()

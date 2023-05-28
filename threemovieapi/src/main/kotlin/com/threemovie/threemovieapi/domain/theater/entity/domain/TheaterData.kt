package com.threemovie.threemovieapi.domain.theater.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLInsert
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "TheaterData")
@SQLInsert(
	sql = "INSERT IGNORE INTO theater_data(addr_en, addr_kr, brch_en, brch_kr, city, movie_theater, theater_code, id)" +
			"VALUES(?,?,?,?,?,?,?,?) "
)
class TheaterData(
	@NotNull
	val movieTheater: String = "MT",
	
	@NotNull
	val city: String = "서울",
	
	@NotNull
	val brchKr: String = "",
	
	@NotNull
	val brchEn: String = "",
	
	@NotNull
	val addrKr: String = "",
	
	val addrEn: String? = null,
	
	@NotNull
	val theaterCode: String = "1234",
) : PrimaryKeyEntity()

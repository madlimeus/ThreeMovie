package com.threemovie.threemovieapi.domain.theater.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLInsert

@Entity
@Table(
	name = "TheaterData",
	uniqueConstraints = [UniqueConstraint(
		name = "theater_data_value_uk",
		columnNames = ["movieTheater", "brchKr", "theaterCode"]
	)]
)
@SQLInsert(
	sql = "INSERT IGNORE INTO theater_data(addr_en, addr_kr, brch_en, brch_kr, city, movie_theater, theater_code, id)" +
			"VALUES(?,?,?,?,?,?,?,?) "
)
class TheaterData(
	@NotNull
	@Column(length = 10)
	val movieTheater: String = "MT",
	
	@NotNull
	@Column(length = 20)
	val city: String = "서울",
	
	@NotNull
	@Column(length = 50)
	val brchKr: String = "",
	
	@NotNull
	val brchEn: String = "",
	
	@NotNull
	val addrKr: String = "",
	
	val addrEn: String? = null,
	
	@NotNull
	@Column(length = 20)
	val theaterCode: String = "1234",
) : PrimaryKeyEntity()

package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*

@Entity
@Table(name = "ShowingData")
data class ShowTime(
	@Column(name = "MovieId")
	var MovieId: String,

	@Column(name = "MovieCode")
	var MovieCode: String,

	@Column(name = "TheaterWhere")
	var TheaterWhere: String,

	@Column(name = "MovieTheater")
	var MovieTheater: String,

	@Column(name = "Dimension")
	var Dimension: String,

	@Column(name = "time")
	var Time: Long,

	@Id
	@Column(name = "Address")
	var Address: String
	) {
	constructor() : this("", "", "", "", "", 202302102051, "")

}

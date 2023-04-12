package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*

@Entity
@Table(name = "UserLogin")
data class UserLogin(
	@Column(name = "UserEmail", length = 50)
	var userEmail: String = "",
	
	@Column(name = "UserPassword")
	var userPassword: String = "",
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	var id: Long = 0L,
)

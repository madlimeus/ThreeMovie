package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "UserSignUpAuth")
data class UserSignUpAuth(
	@Column(name = "UserEmail", length = 50)
	var userEmail: String = "",
	
	@Column(name = "CertificationCode")
	var certificationCode: String = "",
	
	@Column(name = "ExpiredDate")
	var expiredDate: LocalDateTime = LocalDateTime.now(),
	
	@Column(name = "AuthSuccess")
	var authSuccess: Boolean = false,
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	var id: Long = 0L,
)

package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UserSignUpAuth")
data class UserSignUpAuth(
	@Id
	@Column(name = "UserEmail")
	var userEmail: String = "",
	
	@Column(name = "CertificationCode")
	var certificationCode: String = "",
)

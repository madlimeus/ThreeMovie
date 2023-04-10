package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UserLogin")
data class UserLogin(
	@Id
	@Column(name = "UserId")
	var userId: String = "",
	
	@Column(name = "UserPassword")
	var userPassword: String = "",
	
	)

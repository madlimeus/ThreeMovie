package com.threemovie.threemovieapi.domain.user.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "UserSignUpAuth")
class UserSignUpAuth(
	@Column(length = 50)
	@NotNull
	var email: String = "",
	
	@Column(length = 8)
	@NotNull
	var authCode: String = "",
	
	@NotNull
	var expiredDate: LocalDateTime,
	
	@NotNull
	var authSuccess: Boolean = false,
	
	) : PrimaryKeyEntity()

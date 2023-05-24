package com.threemovie.threemovieapi.domain.user.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import com.threemovie.threemovieapi.global.security.config.UserRole
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "UserLogin")
class UserLogin(
	@Column(length = 50)
	@NotNull
	var userEmail: String = "",
	
	@NotNull
	var userPassword: String = "",
	
	@NotNull
	@Enumerated(EnumType.STRING)
	var userRole: UserRole = UserRole.NOT_PERMITTED,
) : PrimaryKeyEntity()

package com.threemovie.threemovieapi.domain.user.entity.domain

import com.threemovie.threemovieapi.global.security.config.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "UserLogin")
data class UserLogin(
	@Column(name = "UserEmail", length = 50)
	var userEmail: String = "",
	
	@Column(name = "UserPassword")
	var userPassword: String = "",
	
	@Column(name = "UserRole")
	@Enumerated(EnumType.STRING)
	var userRole: UserRole = UserRole.NOT_PERMITTED,
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long = 0L,
)

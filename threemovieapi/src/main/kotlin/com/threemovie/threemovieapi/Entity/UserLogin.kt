package com.threemovie.threemovieapi.Entity

import com.threemovie.threemovieapi.Config.UserRole
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	var id: Long = 0L,
)

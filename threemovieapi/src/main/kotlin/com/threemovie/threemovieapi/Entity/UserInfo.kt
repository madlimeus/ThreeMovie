package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "UserInfo")
data class UserInfo(
	@Column(name = "UserCategories")
	var userCategories: String = "",
	
	@Column(name = "UserBrch")
	var userBrch: String = "",
	
	@Column(name = "UserEmail", length = 50)
	var userEmail: String = "",
	
	@Column(name = "UserNickName")
	var userNickName: String = "",
	
	@Column(name = "UserSex")
	var userSex: Boolean = false,
	
	@Column(name = "UserBirth")
	var userBirth: LocalDate = LocalDate.now(),
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	var id: Long = 0L,
)

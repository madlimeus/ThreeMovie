package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "UserData")
data class UserData(
	@Column(name = "UserEmail", length = 50)
	val userEmail: String = "",
	
	@Column(name = "UserNickName", length = 20)
	val userNickName: String = "",
	
	@Column(name = "UserSex")
	val userSex: Boolean? = false,
	
	@Column(name = "UserBirth")
	val userBirth: LocalDate? = LocalDate.now(),
	
	@Column(name = "UserCategories")
	val userCategories: String = "",
	
	@Column(name = "UserBrch")
	val userBrch: String = "",
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long = 0L,
)

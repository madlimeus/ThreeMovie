package com.threemovie.threemovieapi.Entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "UserInfo")
data class UserInfo(
	@Id
	@Column(name = "UserId")
	var userId: String = "",
	
	@Column(name = "UserCategories")
	var userCategories: String = "",
	
	@Column(name = "UserBrch")
	var userBrch: String = "",
	
	@Column(name = "UserEmail")
	var userEmail: String = "",
	
	@Column(name = "UserNickName")
	var userNickName: String = "",
)

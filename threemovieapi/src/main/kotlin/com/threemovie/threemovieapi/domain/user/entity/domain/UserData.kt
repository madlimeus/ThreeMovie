package com.threemovie.threemovieapi.domain.user.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity
@Table(name = "UserData")
class UserData(
	@Column(length = 50)
	@NotNull
	val userEmail: String = "",
	
	@Column(length = 20)
	@NotNull
	val userNickName: String = "",
	
	val userSex: Boolean? = false,
	
	val userBirth: LocalDate? = LocalDate.now(),
	
	@NotNull
	val userCategories: String = "",
	
	@NotNull
	val userBrch: String = "",
	
	) : PrimaryKeyEntity()

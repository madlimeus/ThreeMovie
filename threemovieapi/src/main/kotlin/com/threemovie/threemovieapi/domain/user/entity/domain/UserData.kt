package com.threemovie.threemovieapi.domain.user.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "UserData")
class UserData(
	@Column(length = 20)
	@NotNull
	val nickName: String = "",
	
	val sex: Boolean? = false,
	
	val birth: LocalDate?,
	
	@NotNull
	val categories: String = "",
	
	@NotNull
	val brch: String = "",
	
	) : PrimaryKeyEntity() {
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	var userLogin: UserLogin? = null
}

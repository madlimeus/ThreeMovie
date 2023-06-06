package com.threemovie.threemovieapi.domain.user.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import com.threemovie.threemovieapi.global.security.config.UserRole
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "UserLogin")
class UserLogin(
	@Column(length = 50)
	@NotNull
	private val email: String = "",
	
	@NotNull
	val password: String = "",
) : PrimaryKeyEntity() {
	@NotNull
	@Enumerated(EnumType.STRING)
	var role: UserRole = UserRole.USER
	
	@NotNull
	@OneToOne(mappedBy = "userLogin", orphanRemoval = true, cascade = [CascadeType.ALL])
	@JoinColumn(name = "user_data_id")
	var userData: UserData? = null
}

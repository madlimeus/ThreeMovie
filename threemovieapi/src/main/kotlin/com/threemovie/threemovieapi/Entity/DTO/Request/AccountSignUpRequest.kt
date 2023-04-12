package com.threemovie.threemovieapi.Entity.DTO.Request

import java.time.LocalDate

data class AccountSignUpRequest(
	val email: String,
	val password: String,
	val nickName: String,
	val sex: Boolean,
	val birth: LocalDate,
)

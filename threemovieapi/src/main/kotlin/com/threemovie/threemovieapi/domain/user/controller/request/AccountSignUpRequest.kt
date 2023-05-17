package com.threemovie.threemovieapi.domain.user.controller.request

import java.time.LocalDate

data class AccountSignUpRequest(
	val email: String,
	val password: String,
	val nickName: String,
	val sex: Boolean? = null,
	val birth: LocalDate? = null,
)

package com.threemovie.threemovieapi.domain.user.controller.request

import java.time.LocalDate

data class UpdateUserDataRequest(
	val email: String,
	val nickName: String,
	val sex: Boolean? = null,
	val birth: LocalDate? = null,
)

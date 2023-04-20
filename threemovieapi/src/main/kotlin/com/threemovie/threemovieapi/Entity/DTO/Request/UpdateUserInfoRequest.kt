package com.threemovie.threemovieapi.Entity.DTO.Request

import java.time.LocalDate

data class UpdateUserInfoRequest(
	val email: String,
	val nickName: String,
	val sex: Boolean? = null,
	val birth: LocalDate? = null,
)

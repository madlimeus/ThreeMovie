package com.threemovie.threemovieapi.domain.user.entity.dto

import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDate

data class UserDataDTO @QueryProjection constructor(
	val nickName: String = "",
	
	val sex: Boolean? = false,
	
	val birth: LocalDate?,
	
	val categories: String = "",
	
	val brch: String = "",
)

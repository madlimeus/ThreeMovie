package com.threemovie.threemovieapi.Entity.DTO.Request

data class EmailRequest(
	val email: String,
	val isSignUp: Boolean = false
)

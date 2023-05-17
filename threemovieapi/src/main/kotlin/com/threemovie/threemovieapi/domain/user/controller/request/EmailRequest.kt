package com.threemovie.threemovieapi.domain.user.controller.request

data class EmailRequest(
	val email: String,
	val isSignUp: Boolean = false
)

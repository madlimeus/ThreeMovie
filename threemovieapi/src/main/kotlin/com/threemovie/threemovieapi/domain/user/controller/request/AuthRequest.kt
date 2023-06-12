package com.threemovie.threemovieapi.domain.user.controller.request

data class AuthRequest(
	val email: String,
	val authCode: String
)

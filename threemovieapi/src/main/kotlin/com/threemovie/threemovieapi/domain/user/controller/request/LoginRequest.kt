package com.threemovie.threemovieapi.domain.user.controller.request

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginRequest(
	val email: String,
	val password: String
) {
	fun toAuthentication(): UsernamePasswordAuthenticationToken {
		return UsernamePasswordAuthenticationToken.unauthenticated(email, password)
	}
}

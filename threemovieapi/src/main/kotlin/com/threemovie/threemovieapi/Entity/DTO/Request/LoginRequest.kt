package com.threemovie.threemovieapi.Entity.DTO.Request

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

data class LoginRequest(
	val email: String,
	val password: String
) {
	fun toAuthentication(): UsernamePasswordAuthenticationToken {
		return UsernamePasswordAuthenticationToken.unauthenticated(email, password)
	}
}

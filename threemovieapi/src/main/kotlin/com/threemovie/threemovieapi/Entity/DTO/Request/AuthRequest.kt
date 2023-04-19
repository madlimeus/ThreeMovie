package com.threemovie.threemovieapi.Entity.DTO.Request

data class AuthRequest(
	val email: String,
	val authCode: String
)

package com.threemovie.threemovieapi.global.security.response

data class TokenResponse(
	val accessToken: String,
	val refreshToken: String,
	val nickName: String
)

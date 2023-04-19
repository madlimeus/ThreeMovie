package com.threemovie.threemovieapi.Utils.jwt

data class TokenResponse(
	val refreshToken: String,
	val accessToken: String
)

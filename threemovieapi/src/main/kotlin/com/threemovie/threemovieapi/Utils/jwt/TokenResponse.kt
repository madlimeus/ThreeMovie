package com.threemovie.threemovieapi.Utils.jwt

data class TokenResponse(
	val accessToken: String,
	val refreshToken: String,
	val nickName: String
)

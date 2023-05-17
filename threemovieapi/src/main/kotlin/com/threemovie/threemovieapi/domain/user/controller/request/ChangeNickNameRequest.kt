package com.threemovie.threemovieapi.domain.user.controller.request

data class ChangeNickNameRequest(
	val email: String,
	val nickName: String
)

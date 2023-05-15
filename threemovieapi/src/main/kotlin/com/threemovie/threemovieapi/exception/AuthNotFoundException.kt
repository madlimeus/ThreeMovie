package com.threemovie.threemovieapi.exception

import org.springframework.http.HttpStatus

class AuthNotFoundException : CommonException(
	code = "AUTH_NOT_FOUND",
	message = "이메일 인증을 먼저 진행 해주세요.",
	status = HttpStatus.BAD_REQUEST
)

package com.threemovie.threemovieapi.domain.user.exception

import com.threemovie.threemovieapi.global.exception.exception.CommonException
import org.springframework.http.HttpStatus

class AuthNotFoundException : CommonException(
	code = "AUTH_NOT_FOUND",
	message = "이메일 인증을 먼저 진행 해주세요.",
	status = HttpStatus.BAD_REQUEST
)

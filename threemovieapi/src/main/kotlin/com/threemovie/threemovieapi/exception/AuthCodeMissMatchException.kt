package com.threemovie.threemovieapi.exception

import org.springframework.http.HttpStatus

class AuthCodeMissMatchException : CommonException(
	code = "AUTH_CODE_MISS_MATCH",
	message = "인증 번호가 일치하지 않습니다.",
	status = HttpStatus.BAD_REQUEST
)

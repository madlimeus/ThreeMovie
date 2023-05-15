package com.threemovie.threemovieapi.exception

import org.springframework.http.HttpStatus

class PasswordMissMatchException : CommonException(
	code = "PASSWORD_MISS_MATCH",
	message = "비밀번호가 일치하지 않습니다.",
	status = HttpStatus.BAD_REQUEST
)

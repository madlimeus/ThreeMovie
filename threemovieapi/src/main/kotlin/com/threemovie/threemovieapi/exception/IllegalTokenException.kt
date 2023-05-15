package com.threemovie.threemovieapi.exception

import org.springframework.http.HttpStatus

class IllegalTokenException : CommonException(
	code = "ILLEGAL_TOKEN",
	message = "잘못된 토큰 입니다.",
	status = HttpStatus.BAD_REQUEST
)

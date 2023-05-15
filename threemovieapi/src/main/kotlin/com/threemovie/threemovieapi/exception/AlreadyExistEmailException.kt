package com.threemovie.threemovieapi.exception

import org.springframework.http.HttpStatus

class AlreadyExistEmailException : CommonException(
	code = "ALREADY_EXIST_EMAIL",
	message = "이미 가입된 이메일입니다.",
	status = HttpStatus.BAD_REQUEST
)

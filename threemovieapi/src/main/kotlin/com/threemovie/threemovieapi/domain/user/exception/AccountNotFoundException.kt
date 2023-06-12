package com.threemovie.threemovieapi.domain.user.exception

import com.threemovie.threemovieapi.global.exception.exception.CommonException
import org.springframework.http.HttpStatus

class AccountNotFoundException : CommonException(
	code = "ACCOUNT_NOT_FOUND",
	message = "가입되어 있지 않은 이메일이거나 비밀번호가 틀렸습니다.",
	status = HttpStatus.BAD_REQUEST
)

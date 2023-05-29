package com.threemovie.threemovieapi.domain.user.exception

import com.threemovie.threemovieapi.global.exception.exception.CommonException
import org.springframework.http.HttpStatus

class AccountPasswordMissMatchException : CommonException(
	code = "ACCOUNT_PASSWORD_MISS_MATCH",
	message = "가입되어 있지 않은 이메일 혹은 비밀번호가 틀렸습니다.",
	status = HttpStatus.BAD_REQUEST
)

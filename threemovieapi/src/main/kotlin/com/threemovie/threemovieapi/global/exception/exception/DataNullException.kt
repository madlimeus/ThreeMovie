package com.threemovie.threemovieapi.global.exception.exception

import org.springframework.http.HttpStatus

class DataNullException : CommonException(
	code = "DATA_NULL",
	message = "요청하신 데이터가 없습니다.",
	status = HttpStatus.BAD_REQUEST
)

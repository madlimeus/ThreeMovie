package com.threemovie.threemovieapi.global.exception.exception

import org.springframework.http.HttpStatus

class DataNotFoundException : CommonException(
	code = "DATA_NOT_FOUND",
	message = "요청하신 데이터가 없습니다.",
	status = HttpStatus.NOT_FOUND
)

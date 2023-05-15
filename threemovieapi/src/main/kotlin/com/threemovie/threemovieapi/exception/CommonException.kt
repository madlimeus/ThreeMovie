package com.threemovie.threemovieapi.exception

import org.springframework.http.HttpStatus

open class CommonException(
	val code: String,
	message: String,
	val status: HttpStatus
) : RuntimeException(message)

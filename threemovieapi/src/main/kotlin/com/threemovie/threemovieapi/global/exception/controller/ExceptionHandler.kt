package com.threemovie.threemovieapi.global.exception.controller

import com.threemovie.threemovieapi.global.exception.exception.CommonException
import com.threemovie.threemovieapi.global.exception.exception.CommonExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
	
	@ExceptionHandler(CommonException::class)
	fun commonExceptionHandler(e: CommonException) =
		ResponseEntity(
			CommonExceptionResponse(
				code = e.code,
				message = e.message ?: "에러 발생"
			),
			e.status,
		)
	
	@ExceptionHandler(MethodArgumentNotValidException::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException) =
		CommonExceptionResponse(
			code = "INVALID_REQUEST",
			message = e.bindingResult.fieldError?.defaultMessage ?: "에러 발생"
		)
	
	@ExceptionHandler(HttpMessageNotReadableException::class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException) =
		CommonExceptionResponse(
			code = "INVALID_JSON",
			message = "JSON 형식이 잘못되었습니다."
		)
}

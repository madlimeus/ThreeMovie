package com.threemovie.threemovieapi.domain.movie.exception

import com.threemovie.threemovieapi.global.exception.exception.CommonException
import org.springframework.http.HttpStatus

class ReviewNotFoundException : CommonException(
	code = "REVIEW_NOT_FOUND",
	message = "요청하신 리뷰 데이터가 없습니다.",
	status = HttpStatus.NOT_FOUND
)

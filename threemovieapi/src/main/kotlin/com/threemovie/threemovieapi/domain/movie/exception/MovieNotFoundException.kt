package com.threemovie.threemovieapi.domain.movie.exception

import com.threemovie.threemovieapi.global.exception.exception.CommonException
import org.springframework.http.HttpStatus

class MovieNotFoundException : CommonException(
	code = "MOVIEDATA_NOT_FOUND",
	message = "영화 정보를 찾을 수 없습니다.",
	status = HttpStatus.NOT_FOUND
)

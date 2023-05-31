package com.threemovie.threemovieapi.domain.movie.entity.dto

data class MovieNameInfoVO(
	var movieId: String? = null,
	
	var similarity: Double = 0.0,
)

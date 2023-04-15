package com.threemovie.threemovieapi.Entity.VO

data class MovieNameInfoVO(
	var movieId: String? = null,
	
	var nameKR: String? = null,
	
	var nameEN: String? = null,
	
	var similarity: Double = 0.0,
)

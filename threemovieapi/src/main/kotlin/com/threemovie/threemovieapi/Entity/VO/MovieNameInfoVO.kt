package com.threemovie.threemovieapi.Entity.VO

data class MovieNameDataVO(
	var movieId: String? = null,
	
	var nameKR: String? = null,
	
	var nameEN: String? = null,
	
	var similarity: Double = 0.0,
)

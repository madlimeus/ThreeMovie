package com.threemovie.threemovieapi.Entity.DTO

data class MovieNameInfoDTO(
	var movieId: String? = null,

	var nameKR: String? = null,

	var nameEN: String? = null,

	var similarity: Double = 0.0,
)

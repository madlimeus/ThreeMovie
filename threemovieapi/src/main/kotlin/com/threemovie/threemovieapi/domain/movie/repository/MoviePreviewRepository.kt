package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MoviePreview
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MoviePreviewRepository : JpaRepository<MoviePreview, String> {
	@Transactional
	@Modifying
	@Query(value = "truncate MoviePreview", nativeQuery = true)
	fun truncate()
	
}

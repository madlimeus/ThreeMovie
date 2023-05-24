package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MoviePreview
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MoviePreviewRepository : JpaRepository<MoviePreview, UUID> {
	@Transactional
	@Modifying
	@Query(value = "truncate movie_preview", nativeQuery = true)
	fun truncate()
	
}

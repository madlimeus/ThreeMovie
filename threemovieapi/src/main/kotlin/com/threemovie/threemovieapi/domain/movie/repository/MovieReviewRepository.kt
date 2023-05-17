package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieReview
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MovieReviewRepository : JpaRepository<MovieReview, String> {
	@Transactional
	@Modifying
	@Query(value = "truncate MovieReview", nativeQuery = true)
	fun truncate()
}

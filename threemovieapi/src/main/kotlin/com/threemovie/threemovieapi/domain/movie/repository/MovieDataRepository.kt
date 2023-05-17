package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MovieDataRepository : JpaRepository<MovieData, String> {
	@Transactional
	@Modifying
	@Query(value = "truncate Moviedata", nativeQuery = true)
	fun truncate()
}

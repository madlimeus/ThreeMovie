package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MovieDataRepository : JpaRepository<MovieData, UUID> {
	@Transactional
	@Modifying
	@Query(value = "truncate movie_data", nativeQuery = true)
	fun truncate()
}

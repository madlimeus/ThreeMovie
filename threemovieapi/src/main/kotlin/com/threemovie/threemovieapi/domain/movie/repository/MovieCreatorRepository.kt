package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieCreator
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MovieCreatorRepository : JpaRepository<MovieCreator, String> {
	@Transactional
	@Modifying
	@Query(value = "truncate MovieCreator", nativeQuery = true)
	fun truncate()
	
}

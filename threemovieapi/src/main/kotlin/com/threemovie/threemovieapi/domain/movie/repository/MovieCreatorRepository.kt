package com.threemovie.threemovieapi.domain.movie.repository

import com.threemovie.threemovieapi.domain.movie.entity.domain.MovieCreator
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MovieCreatorRepository : JpaRepository<MovieCreator, UUID> {
	@Transactional
	@Modifying
	@Query(value = "truncate movie_creator", nativeQuery = true)
	fun truncate()
	
}

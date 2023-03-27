package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.MovieInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface MovieInfoRepository : JpaRepository<MovieInfo, String> {
	@Transactional
	@Modifying
	@Query(value = "truncate MovieInfo", nativeQuery = true)
	fun truncate()
}

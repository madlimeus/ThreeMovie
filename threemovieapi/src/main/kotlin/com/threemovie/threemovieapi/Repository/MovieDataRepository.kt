package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.MovieData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface MovieDataRepository : JpaRepository<MovieData, String> {
    @Transactional
    @Modifying
    @Query(value = "truncate MovieData", nativeQuery = true)
    fun truncate()
}
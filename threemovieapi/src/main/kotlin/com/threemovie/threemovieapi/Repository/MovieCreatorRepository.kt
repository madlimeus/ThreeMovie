package com.threemovie.threemovieapi.Repository

import org.springframework.data.jpa.repository.JpaRepository
import com.threemovie.threemovieapi.Entity.MovieCreator
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query


interface MovieCreatorRepository : JpaRepository<MovieCreator, String> {
    @Transactional
    @Modifying
    @Query(value = "truncate MovieCreator", nativeQuery = true)
    fun truncate()

}
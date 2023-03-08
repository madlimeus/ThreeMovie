package com.threemovie.threemovieapi.Repository

import org.springframework.data.jpa.repository.JpaRepository
import com.threemovie.threemovieapi.Entity.MoviePreview
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MoviePreviewRepository : JpaRepository<MoviePreview, String> {
    @Transactional
    @Modifying
    @Query(value = "truncate MoviePreview", nativeQuery = true)
    fun truncate()

}
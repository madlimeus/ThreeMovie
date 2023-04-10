
package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.MovieReview
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

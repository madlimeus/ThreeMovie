package com.threemovie.threemovieapi.domain.showtime.repository

import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ShowTimeRepository : JpaRepository<ShowTime, UUID> {
	@Transactional
	@Modifying
	@Query(value = "truncate show_time", nativeQuery = true)
	fun truncateShowTime()
}

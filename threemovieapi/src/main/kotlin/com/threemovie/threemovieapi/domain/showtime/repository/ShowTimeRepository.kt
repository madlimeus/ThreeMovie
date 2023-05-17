package com.threemovie.threemovieapi.domain.showtime.repository

import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimePK
import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ShowTimeRepository : JpaRepository<ShowTime, ShowTimePK> {
	@Transactional
	@Modifying
	@Query(value = "truncate ShowTimes", nativeQuery = true)
	fun truncateShowTime()
}

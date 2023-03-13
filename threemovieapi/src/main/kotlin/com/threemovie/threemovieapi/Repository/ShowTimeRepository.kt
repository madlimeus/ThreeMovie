package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.ShowTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ShowTimeRepository : JpaRepository<ShowTime, String> {
	@Transactional
	@Modifying
	@Query(value = "truncate ShowTimes", nativeQuery = true)
	fun truncateShowTime()
}

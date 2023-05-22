package com.threemovie.threemovieapi.domain.showtime.repository

import com.threemovie.threemovieapi.domain.showtime.entity.domain.TmpShowTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TmpShowTimeRepository : JpaRepository<TmpShowTime, UUID> {
	@Transactional
	@Modifying
	@Query(value = "truncate TmpShowTime", nativeQuery = true)
	fun truncateTmpShowTime()
	
	@Transactional
	@Modifying
	@Query(
		value = "RENAME TABLE ShowTime TO BeforeShowTime, TmpShowTime TO ShowTime, BeforeShowTime TO TmpShowTime",
		nativeQuery = true
	)
	fun chgShowTimeTable()
}

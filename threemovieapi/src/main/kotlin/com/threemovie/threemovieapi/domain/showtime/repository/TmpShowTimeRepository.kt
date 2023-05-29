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
	@Query(value = "truncate tmp_show_time", nativeQuery = true)
	fun truncateTmpShowTime()
	
	@Transactional
	@Modifying
	@Query(
		value = "RENAME TABLE show_time TO before_show_time, tmp_show_time TO show_time, before_show_time TO tmp_show_time",
		nativeQuery = true
	)
	fun chgShowTimeTable()
}

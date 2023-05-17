package com.threemovie.threemovieapi.domain.showtime.repository

import com.threemovie.threemovieapi.domain.showtime.entity.dto.ShowTimePK
import com.threemovie.threemovieapi.domain.showtime.entity.domain.TmpShowTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface TmpShowTimeRepository : JpaRepository<TmpShowTime, ShowTimePK> {
	@Transactional
	@Modifying
	@Query(value = "truncate TmpShowTimes", nativeQuery = true)
	fun truncateTmpShowTime()
	
	@Transactional
	@Modifying
	@Query(
		value = "RENAME TABLE ShowTimes TO BeforeShowTimes, TmpShowTimes TO ShowTimes, BeforeShowTimes TO TmpShowTimes",
		nativeQuery = true
	)
	fun chgShowTimeTable()
}

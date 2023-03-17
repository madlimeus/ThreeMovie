package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.PK.ShowTimePK
import com.threemovie.threemovieapi.Entity.TmpShowTime
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface TmpShowTimeRepository : JpaRepository<TmpShowTime, ShowTimePK> {
	@Transactional
	@Modifying
	@Query(value = "truncate TmpShowTimes", nativeQuery = true)
	fun truncateTmpShowTime()

	@Modifying
	@Query(value = "RENAME TABLE ShowTimes TO TmpShowTimes, TmpShowTimes TO ShowTimes  ", nativeQuery = true)
	fun chgShowTimeTable()
}

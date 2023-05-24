package com.threemovie.threemovieapi.domain.showtime.repository

import com.threemovie.threemovieapi.domain.showtime.entity.domain.TmpShowTime
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class TmpShowTimeRepositorySaveAll {
	@Autowired
	lateinit var jdbcTemplate: JdbcTemplate
	
	@Transactional
	fun saveAll(tmpShowTimes: List<TmpShowTime>) {
		println("start")
		var tmpShowTimeArray = ArrayList<TmpShowTime>()
		for (i: Int in tmpShowTimes.indices) {
			tmpShowTimeArray.add(tmpShowTimes[i])
			if ((i + 1) % 1000 == 0) {
				batchInsert(tmpShowTimeArray)
				tmpShowTimeArray.clear()
			} else if (i == tmpShowTimes.size - 1) {
				batchInsert(tmpShowTimeArray)
				return
			}
		}
	}
	
	private fun batchInsert(tmpShowTimes: List<TmpShowTime>) {
		println("왜이래 ㅆㅂ")
		val sql =
			"INSERT IGNORE INTO tmp_show_time(brchen, brchkr, city, date, items, movieen, movie_id, moviekr, movie_theater, play_kind, screenen, screenkr, total_seat, id)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
		jdbcTemplate.batchUpdate(sql,
			object : BatchPreparedStatementSetter {
				override fun setValues(ps: PreparedStatement, i: Int) {
					ps.setString(1, tmpShowTimes[i].brchEN)
					ps.setString(2, tmpShowTimes[i].brchKR)
					ps.setString(3, tmpShowTimes[i].city)
					ps.setString(4, tmpShowTimes[i].date)
					ps.setString(5, tmpShowTimes[i].items)
					ps.setString(6, tmpShowTimes[i].movieEN)
					ps.setString(7, tmpShowTimes[i].movieId)
					ps.setString(8, tmpShowTimes[i].movieKR)
					ps.setString(9, tmpShowTimes[i].movieTheater)
					ps.setString(10, tmpShowTimes[i].playKind)
					ps.setString(11, tmpShowTimes[i].screenEN)
					ps.setString(12, tmpShowTimes[i].screenKR)
					ps.setInt(13, tmpShowTimes[i].totalSeat)
					ps.setString(14, tmpShowTimes[i].id.toString())
				}
				
				override fun getBatchSize(): Int = tmpShowTimes.size
			}
		)
	}
}

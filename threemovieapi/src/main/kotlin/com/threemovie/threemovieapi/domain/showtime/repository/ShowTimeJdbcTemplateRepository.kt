package com.threemovie.threemovieapi.domain.showtime.repository

import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTime
import com.threemovie.threemovieapi.domain.showtime.entity.domain.ShowTimeReserve
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class ShowTimeJdbcTemplateRepository {
	@Autowired
	lateinit var jdbcTemplate: JdbcTemplate
	
	fun saveAll(showTimesArg: List<ShowTime>) {
		var showTimes = ArrayList<ShowTime>()
		var showTimeReservations = ArrayList<ShowTimeReserve>()
		
		for (i: Int in showTimesArg.indices) {
			showTimes.add(showTimesArg[i])
			showTimeReservations.addAll(showTimesArg[i].showTimeReserve)
			
			if ((i + 1) % 500 == 0) {
				batchInsert(showTimes)
				batchReservationInsert(showTimeReservations)
				showTimes.clear()
				showTimeReservations.clear()
			} else if (i == showTimesArg.size - 1) {
				batchInsert(showTimes)
				batchReservationInsert(showTimeReservations)
				return
			}
		}
	}
	
	private fun batchInsert(showTimes: List<ShowTime>) {
		val sql =
			"INSERT IGNORE INTO show_time(movie_id, play_kind, screen_en, screen_kr, show_ymd, theater_data_id, total_seat, id)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
		jdbcTemplate.batchUpdate(sql,
			object : BatchPreparedStatementSetter {
				override fun setValues(ps: PreparedStatement, i: Int) {
					ps.setString(1, showTimes[i].movieData?.movieId)
					ps.setString(2, showTimes[i].playKind)
					ps.setString(3, showTimes[i].screenEn)
					ps.setString(4, showTimes[i].screenKr)
					ps.setInt(5, showTimes[i].showYmd)
					ps.setString(6, showTimes[i].theaterData.id.toString())
					ps.setInt(7, showTimes[i].totalSeat)
					ps.setString(8, showTimes[i].id.toString())
					
				}
				
				override fun getBatchSize(): Int = showTimes.size
			}
		)
	}
	
	private fun batchReservationInsert(tmpShowTimeReservations: List<ShowTimeReserve>) {
		val sql =
			"INSERT INTO show_time_reserve(start_time, end_time, rest_seat, updated_at, ticket_page, show_time_id, id)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?)" +
					"ON DUPLICATE KEY UPDATE" +
					" rest_seat = VALUES(rest_seat), updated_at = VALUES(updated_at)"
		
		jdbcTemplate.batchUpdate(sql,
			object : BatchPreparedStatementSetter {
				override fun setValues(ps: PreparedStatement, i: Int) {
					ps.setLong(1, tmpShowTimeReservations[i].startTime)
					ps.setLong(2, tmpShowTimeReservations[i].endTime)
					ps.setInt(3, tmpShowTimeReservations[i].restSeat)
					ps.setLong(4, tmpShowTimeReservations[i].updatedAt)
					ps.setString(5, tmpShowTimeReservations[i].ticketPage)
					ps.setString(6, tmpShowTimeReservations[i].showTime.id.toString())
					ps.setString(7, tmpShowTimeReservations[i].id.toString())
				}
				
				override fun getBatchSize(): Int = tmpShowTimeReservations.size
			}
		)
	}
}

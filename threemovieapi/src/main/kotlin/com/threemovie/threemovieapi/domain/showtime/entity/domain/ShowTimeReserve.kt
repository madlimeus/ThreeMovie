package com.threemovie.threemovieapi.domain.showtime.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLInsert

@Entity
@Table(name = "ShowTimeReserve")
@SQLInsert(
	sql = "INSERT INTO show_time_reserve(end_time, rest_seat, show_time_id, start_time, ticket_page, updated_at, id)" +
			"VALUES (?, ?, ?, ?, ?, ?, ?)" +
			"ON DUPLICATE KEY UPDATE" +
			" rest_seat = VALUES(rest_seat), updated_at = VALUES(updated_at)"
)
class ShowTimeReserve(
	@NotNull
	val startTime: Long = 0L,
	
	@NotNull
	val endTime: Long = 0L,
	
	@NotNull
	val restSeat: Int = 0,
	
	@NotNull
	val updatedAt: Long = 0L,
	
	@Column(unique = true)
	@NotNull
	val ticketPage: String = "",
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "show_time_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
	var showTime: ShowTime
) : PrimaryKeyEntity()

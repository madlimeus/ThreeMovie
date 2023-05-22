package com.threemovie.threemovieapi.global.entity

import com.threemovie.threemovieapi.global.repository.PrimaryKeyEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "LastUpdateTime")
class LastUpdateTime(
	val code: String = "MT",
	val time: Long = 202302110107,
) : PrimaryKeyEntity()

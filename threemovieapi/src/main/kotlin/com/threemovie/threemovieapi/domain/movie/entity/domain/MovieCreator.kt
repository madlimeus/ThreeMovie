package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLInsert

@Entity
@Table(
	name = "MovieCreator",
	uniqueConstraints = [UniqueConstraint(
		name = "creator_uk",
		columnNames = ["nameKr", "roleKr", "movie_id"]
	)]
)
@SQLInsert(
	sql = "INSERT IGNORE INTO movie_creator(link, movie_id, name_en, name_kr, role_kr, id)"
			+ " VALUES (?, ?, ?, ?, ?, ?)"
)
class MovieCreator(
	@NotNull
	val nameKr: String,
	
	val nameEn: String?,
	
	val roleKr: String?,
	
	@Column(length = 500)
	val link: String?,
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "movieId")
	val movieData: MovieData? = null
) : PrimaryKeyEntity()

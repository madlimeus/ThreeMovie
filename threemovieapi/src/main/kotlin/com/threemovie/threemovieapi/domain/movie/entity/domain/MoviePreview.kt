package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLInsert

@Entity
@Table(name = "MoviePreview")
@SQLInsert(
	sql = "INSERT IGNORE INTO movie_preview(link, movie_id, type, id)"
			+ " VALUES (?, ?, ?, ?)"
)
class MoviePreview(
	@NotNull
	val type: String,
	
	@NotNull
	@Column(unique = true, length = 500)
	val link: String,
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movie_id", referencedColumnName = "movieId")
	val movieData: MovieData? = null
) : PrimaryKeyEntity()

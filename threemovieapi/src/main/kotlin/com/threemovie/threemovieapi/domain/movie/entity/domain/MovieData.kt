package com.threemovie.threemovieapi.domain.movie.entity.domain

import com.threemovie.threemovieapi.global.entity.PrimaryKeyEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.SQLInsert

@Entity
@Table(name = "MovieData")
@SQLInsert(
	sql = "INSERT IGNORE INTO movie_data(admission_code, category, country, making_note, movie_id, name_en, name_kr, netizen_avg_rate, poster, release_date, reservation_rank, reservation_rate, running_time, summary, total_audience, id)" +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
)
class MovieData(
	@NotNull
	@Column(unique = true)
	val movieId: String = "",

	val netizenAvgRate: Double? = 0.0,

	val reservationRate: Double? = 0.0,

	@Column(columnDefinition = "longtext")
	val summary: String? = "",

	@NotNull
	val nameKr: String = "",

	val nameEn: String? = "",

	val releaseDate: Long = 202303030506,

	val poster: String? = "",

	val category: String? = "",

	@Column(columnDefinition = "longtext")
	val makingNote: String? = "",

	val runningTime: String? = "",

	val admissionCode: String? = "",

	val country: String? = "",

	val reservationRank: String? = "",

	val totalAudience: String? = "",

	) : PrimaryKeyEntity() {

	@OneToMany(
		mappedBy = "movieData",
		orphanRemoval = true,
		cascade = [CascadeType.ALL],
		fetch = FetchType.LAZY
	)
	var creators: MutableList<MovieCreator> = ArrayList()
		protected set

	@OneToMany(
		mappedBy = "movieData",
		orphanRemoval = true,
		cascade = [CascadeType.ALL],
		fetch = FetchType.LAZY
	)
	var previews: MutableList<MoviePreview> = ArrayList()
		protected set

	@OneToMany(
		mappedBy = "movieData",
		orphanRemoval = true,
		cascade = [CascadeType.ALL],
		fetch = FetchType.LAZY,
	)
	var reviews: MutableList<MovieReview> = ArrayList()

	fun addCreators(creators: List<MovieCreator>) {
		this.creators.addAll(creators)
	}

	fun addPreviews(previews: List<MoviePreview>) {
		this.previews.addAll(previews)
	}

	fun addReviews(reviews: List<MovieReview>) {
		this.reviews.addAll(reviews)
	}
}
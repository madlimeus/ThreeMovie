package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*

@Entity
@Table(name = "MoviePreview")
data class MoviePreview(
    @Id
    @Column(name = "MovieId")
    val movieId: String = "",

    @Column(name = "Steelcuts")
    val steelcuts: String = "",

    @Column(name = "Trailer")
    val trailer: String = ""
)

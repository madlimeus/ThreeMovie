package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*

@Entity
@Table(name = "MoviePreview")
data class MoviePreview(
    @Id
    @Column(name = "MovieId")
    val MovieId: String = "",

    @Column(name = "Steelcuts")
    val Steelcuts: String = "",

    @Column(name = "Trailer")
    val Trailer: String = ""
)

package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*

@Entity
@Table(name = "MovieInfo")
data class MovieData(
    @Id
    @Column(name = "MovieId")
    val movieId: String = "",

    @Column(name = "Summary")
    val summary: String = "",

    @Column(name = "NameKR")
    val nameKr: String = "",

    @Column(name = "NameEN")
    val nameEn: String = "",

    @Column(name = "ReleaseDate")
    val releaseDate: String? = "",

    @Column(name = "Poster")
    val poster: String? = "",

    @Column(name = "Category")
    val category: String = "",
    )

package com.threemovie.threemovieapi.Entity

import jakarta.persistence.*

@Entity
@Table(name = "MovieInfo")
data class MovieData(
    @Id
    @Column(name = "MovieId")
    val MovieId: String = "",

    @Column(name = "Summary")
    val Summary: String = "",

    @Column(name = "NameKR")
    val NameKR: String = "",

    @Column(name = "NameEN")
    val NameEN: String = "",

    @Column(name = "ReleaseDate")
    val ReleaseDate: String? = "",

    @Column(name = "Poster")
    val Poster: String? = "",

    @Column(name = "Category")
    val Category: String = "",
    )

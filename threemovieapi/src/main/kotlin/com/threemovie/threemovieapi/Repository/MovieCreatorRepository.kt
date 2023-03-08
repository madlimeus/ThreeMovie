package com.threemovie.threemovieapi.Repository

import org.springframework.data.jpa.repository.JpaRepository
import com.threemovie.threemovieapi.Entity.MovieCreator


interface MovieCreatorRepository : JpaRepository<MovieCreator, String> {
}
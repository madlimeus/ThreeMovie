package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.MovieCreator
import org.springframework.data.jpa.repository.JpaRepository


interface MovieCreatorRepository : JpaRepository<MovieCreator, String> {
}

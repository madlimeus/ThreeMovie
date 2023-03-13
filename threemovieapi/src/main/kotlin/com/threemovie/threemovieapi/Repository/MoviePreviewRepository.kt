package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.MoviePreview
import org.springframework.data.jpa.repository.JpaRepository

interface MoviePreviewRepository : JpaRepository<MoviePreview, String> {

}

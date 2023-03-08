package com.threemovie.threemovieapi.Repository

import org.springframework.data.jpa.repository.JpaRepository
import com.threemovie.threemovieapi.Entity.MoviePreview

interface MoviePreviewRepository : JpaRepository<MoviePreview, String> {

}
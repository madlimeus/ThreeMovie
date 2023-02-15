package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.ShowTime
import com.threemovie.threemovieapi.Repository.Support.ShowTimeRepositorySupport
import org.springframework.data.jpa.repository.JpaRepository

interface ShowTimeRepository : JpaRepository<ShowTime, String> {
}

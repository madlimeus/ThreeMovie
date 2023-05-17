package com.threemovie.threemovieapi.domain.theater.repository

import com.threemovie.threemovieapi.domain.theater.entity.dto.TheaterDataPK
import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import org.springframework.data.jpa.repository.JpaRepository

interface TheaterDataRepository : JpaRepository<TheaterData, TheaterDataPK> {
}

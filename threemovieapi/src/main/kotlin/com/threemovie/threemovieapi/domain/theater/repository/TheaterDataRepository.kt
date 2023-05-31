package com.threemovie.threemovieapi.domain.theater.repository

import com.threemovie.threemovieapi.domain.theater.entity.domain.TheaterData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TheaterDataRepository : JpaRepository<TheaterData, UUID>

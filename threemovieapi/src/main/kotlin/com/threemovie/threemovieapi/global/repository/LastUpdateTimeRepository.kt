package com.threemovie.threemovieapi.global.repository

import com.threemovie.threemovieapi.global.entity.LastUpdateTime
import org.springframework.data.jpa.repository.JpaRepository

interface LastUpdateTimeRepository : JpaRepository<LastUpdateTime, Long>

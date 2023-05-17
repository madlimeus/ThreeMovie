package com.threemovie.threemovieapi.global.repository

import com.threemovie.threemovieapi.global.entity.UpdateTime
import org.springframework.data.jpa.repository.JpaRepository

interface UpdateTimeRepository : JpaRepository<UpdateTime, Long>

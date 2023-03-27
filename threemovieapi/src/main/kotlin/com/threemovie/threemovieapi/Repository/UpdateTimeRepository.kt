package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.UpdateTime
import org.springframework.data.jpa.repository.JpaRepository

interface UpdateTimeRepository : JpaRepository<UpdateTime, Long>

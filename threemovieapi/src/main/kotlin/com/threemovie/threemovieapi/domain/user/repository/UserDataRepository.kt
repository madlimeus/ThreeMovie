package com.threemovie.threemovieapi.domain.user.repository

import com.threemovie.threemovieapi.domain.user.entity.domain.UserData
import org.springframework.data.jpa.repository.JpaRepository

interface UserDataRepository : JpaRepository<UserData, Long>

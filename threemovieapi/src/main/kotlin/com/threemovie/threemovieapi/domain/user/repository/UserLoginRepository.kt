package com.threemovie.threemovieapi.domain.user.repository

import com.threemovie.threemovieapi.domain.user.entity.domain.UserLogin
import org.springframework.data.jpa.repository.JpaRepository

interface UserLoginRepository : JpaRepository<UserLogin, Long>

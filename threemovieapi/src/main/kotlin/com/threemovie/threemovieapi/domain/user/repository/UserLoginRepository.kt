package com.threemovie.threemovieapi.domain.user.repository

import com.threemovie.threemovieapi.domain.user.entity.domain.UserLogin
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserLoginRepository : JpaRepository<UserLogin, UUID>

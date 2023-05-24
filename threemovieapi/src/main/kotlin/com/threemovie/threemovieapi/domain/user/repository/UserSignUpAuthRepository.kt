package com.threemovie.threemovieapi.domain.user.repository

import com.threemovie.threemovieapi.domain.user.entity.domain.UserSignUpAuth
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserSignUpAuthRepository : JpaRepository<UserSignUpAuth, UUID>

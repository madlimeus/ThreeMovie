package com.threemovie.threemovieapi.domain.user.repository

import com.threemovie.threemovieapi.domain.user.entity.domain.UserSignUpAuth
import org.springframework.data.jpa.repository.JpaRepository

interface UserSignUpAuthRepository : JpaRepository<UserSignUpAuth, Long>

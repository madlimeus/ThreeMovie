package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.UserSignUpAuth
import org.springframework.data.jpa.repository.JpaRepository

interface UserSignUpAuthRepository : JpaRepository<UserSignUpAuth, Long>

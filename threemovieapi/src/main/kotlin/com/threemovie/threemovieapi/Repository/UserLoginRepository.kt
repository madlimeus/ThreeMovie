package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.UserLogin
import org.springframework.data.jpa.repository.JpaRepository

interface UserLoginRepository : JpaRepository<UserLogin, String>

package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.UserData
import org.springframework.data.jpa.repository.JpaRepository

interface UserDataRepository : JpaRepository<UserData, Long>

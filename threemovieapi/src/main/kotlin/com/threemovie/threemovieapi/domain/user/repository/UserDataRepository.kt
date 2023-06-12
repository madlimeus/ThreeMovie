package com.threemovie.threemovieapi.domain.user.repository

import com.threemovie.threemovieapi.domain.user.entity.domain.UserData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserDataRepository : JpaRepository<UserData, UUID>

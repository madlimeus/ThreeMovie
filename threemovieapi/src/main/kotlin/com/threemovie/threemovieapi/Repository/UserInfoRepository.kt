package com.threemovie.threemovieapi.Repository

import com.threemovie.threemovieapi.Entity.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo, String>

package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Config.UserRole
import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest
import com.threemovie.threemovieapi.Entity.UserData
import com.threemovie.threemovieapi.Entity.UserLogin
import com.threemovie.threemovieapi.Repository.Support.UserDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserSignUpRepositorySupport
import com.threemovie.threemovieapi.Repository.UserDataRepository
import com.threemovie.threemovieapi.Repository.UserLoginRepository
import com.threemovie.threemovieapi.Utils.jwt.JwtTokenProvider
import com.threemovie.threemovieapi.Utils.jwt.RedisUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAuthService(
	val userSignUpRepositorySupport: UserSignUpRepositorySupport,
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userLoginRepository: UserLoginRepository,
	val userdataRepositorySupport: UserDataRepositorySupport,
	val userdataRepository: UserDataRepository,
	val jwtTokenProvider: JwtTokenProvider,
	val passwordEncoder: BCryptPasswordEncoder,
	val redisUtil: RedisUtil
) {
	fun signUpAccount(signUpRequest: AccountSignUpRequest) {
		val (email, pass, nickName, sex, birth) = signUpRequest
		val encodePass = passwordEncoder.encode(pass)
		
		val userLogin = UserLogin(email, encodePass, UserRole.USER)
		val userdata = UserData(email, nickName, sex, birth)
		userLoginRepository.save(userLogin)
		userdataRepository.save(userdata)
		deletePrevAuth(email)
	}
	
	fun signOutAccount(accessToken: String) {
		try {
			val email = jwtTokenProvider.getEmail(accessToken)
			
			userLoginRepositorySupport.deleteUserLogin(email)
			userdataRepositorySupport.deleteUserData(email)
			redisUtil.deleteData(email)
		} catch (e: Exception) {
			return
		}
		
	}
	
	fun loginAccount(email: String, pass: String): Boolean {
		try {
			val userLogin = userLoginRepositorySupport.getUserLoginByEmail(email)
			
			if (! passwordEncoder.matches(pass, userLogin.userPassword))
				return false
		} catch (e: NullPointerException) {
			return false
		}
		
		return true
	}
	
	fun logoutAccount(accessToken: String): Boolean {
		if (! jwtTokenProvider.validateToken(accessToken))
			throw IllegalArgumentException("잘못된 요청 입니다.")
		
		val email = jwtTokenProvider.getEmail(accessToken)
		if (! redisUtil.getData(email).isNullOrEmpty())
			redisUtil.deleteData(email)
		
		val expiration = jwtTokenProvider.getExpiration(accessToken)
		redisUtil.setDataExpire(accessToken, "logout", expiration)
		
		
		return true
	}
	
	fun reissue(refreshToken: String): Boolean {
		if (! jwtTokenProvider.validateToken(refreshToken))
			return false
		return true
	}
	
	fun existsAuth(email: String): Boolean {
		return userSignUpRepositorySupport.existsSuccessCode(email)
	}
	
	fun checkAuth(email: String, authCode: String): Boolean {
		val ret = userSignUpRepositorySupport.checkAuthCode(email, authCode)
		if (ret)
			updateAuth(email)
		return ret
	}
	
	fun updateAuth(email: String) {
		userSignUpRepositorySupport.updateAuthSuccess(email)
	}
	
	fun deletePrevAuth(email: String) {
		userSignUpRepositorySupport.deletePrevAuth(email)
	}
}

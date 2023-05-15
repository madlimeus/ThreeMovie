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
import com.threemovie.threemovieapi.exception.*
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
		val userLogin = userLoginRepositorySupport.getUserLoginByEmail(email) ?: throw AccountNotFoundException()
		if (! passwordEncoder.matches(
				pass,
				userLogin.userPassword
			)
		)
			throw AccountPasswordMissMatchException()
		
		return true
	}
	
	fun logoutAccount(accessToken: String) {
		if (! jwtTokenProvider.validateToken(accessToken))
			throw IllegalTokenException()
		
		val email = jwtTokenProvider.getEmail(accessToken)
		if (! redisUtil.getData(email).isNullOrEmpty())
			redisUtil.deleteData(email)
		
		val expiration = jwtTokenProvider.getExpiration(accessToken)
		redisUtil.setDataExpire(accessToken, "logout", expiration)
	}
	
	fun reissue(refreshToken: String): Boolean {
		if (refreshToken.isNullOrEmpty() || ! jwtTokenProvider.validateToken(refreshToken))
			throw IllegalTokenException()
		return true
	}
	
	fun existsAuth(email: String): Boolean {
		val ret = userSignUpRepositorySupport.existsSuccessCode(email)
		if (! ret)
			throw AuthNotFoundException()
		return true
	}
	
	fun checkAuth(email: String, authCode: String) {
		val ret = userSignUpRepositorySupport.checkAuthCode(email, authCode)
		if (ret)
			updateAuth(email)
		else
			throw AuthCodeMissMatchException()
	}
	
	fun updateAuth(email: String) {
		userSignUpRepositorySupport.updateAuthSuccess(email)
	}
	
	fun deletePrevAuth(email: String) {
		userSignUpRepositorySupport.deletePrevAuth(email)
	}
}

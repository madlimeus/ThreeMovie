package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest
import com.threemovie.threemovieapi.Entity.UserInfo
import com.threemovie.threemovieapi.Entity.UserLogin
import com.threemovie.threemovieapi.Repository.Support.UserInfoRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserSignUpRepositorySupport
import com.threemovie.threemovieapi.Repository.UserInfoRepository
import com.threemovie.threemovieapi.Repository.UserLoginRepository
import com.threemovie.threemovieapi.Service.UserAuthService
import com.threemovie.threemovieapi.Utils.jwt.JwtTokenProvider
import com.threemovie.threemovieapi.Utils.jwt.RedisUtil
import com.threemovie.threemovieapi.config.UserRole
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAuthServiceimpl(
	val userSignUpRepositorySupport: UserSignUpRepositorySupport,
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userLoginRepository: UserLoginRepository,
	val userInfoRepositorySupport: UserInfoRepositorySupport,
	val userInfoRepository: UserInfoRepository,
	val jwtTokenProvider: JwtTokenProvider,
	val passwordEncoder: BCryptPasswordEncoder,
	val redisUtil: RedisUtil
) : UserAuthService {
	override fun signUpAccount(signUpRequest: AccountSignUpRequest) {
		val (email, pass, nickName, sex, birth) = signUpRequest
		val encodePass = passwordEncoder.encode(pass)
		
		val userLogin = UserLogin(email, encodePass, UserRole.ROLE_USER)
		val userInfo = UserInfo(email, nickName, sex, birth)
		userLoginRepository.save(userLogin)
		userInfoRepository.save(userInfo)
		deletePrevAuth(email)
	}
	
	override fun signOutAccount(refreshToken: String) {
		try {
			val email = jwtTokenProvider.getEmail(refreshToken)
			
			userLoginRepositorySupport.deleteUserLogin(email)
			userInfoRepositorySupport.deleteUserInfo(email)
			redisUtil.deleteData(email)
		} catch (e: Exception) {
			return
		}
		
	}
	
	override fun loginAccount(email: String, pass: String): Boolean {
		val userLogin = userLoginRepositorySupport.getUserLoginByEmail(email)
		
		if (! passwordEncoder.matches(pass, userLogin?.userPassword))
			throw BadCredentialsException("계정 혹은 비밀번호가 틀렸습니다.")
		
		return true
	}
	
	override fun logoutAccount(accessToken: String): Boolean {
		if (! jwtTokenProvider.validateToken(accessToken))
			throw IllegalArgumentException("잘못된 요청 입니다.")
		
		val email = jwtTokenProvider.getEmail(accessToken)
		if (! redisUtil.getData(email).isNullOrEmpty())
			redisUtil.deleteData(email)
		
		val expiration = jwtTokenProvider.getExpiration(accessToken)
		redisUtil.setDataExpire(accessToken, "logout", expiration)
		
		
		return true
	}
	
	override fun reissue(refreshToken: String): Boolean {
		if (! jwtTokenProvider.validateToken(refreshToken))
			return false
		return true
	}
	
	override fun existsAuth(email: String): Boolean {
		return userSignUpRepositorySupport.existsSuccessCode(email)
	}
	
	override fun checkAuth(email: String, authCode: String): Boolean {
		val ret = userSignUpRepositorySupport.checkAuthCode(email, authCode)
		if (ret)
			updateAuth(email)
		return ret
	}
	
	override fun updateAuth(email: String) {
		userSignUpRepositorySupport.updateAuthSuccess(email)
	}
	
	override fun deletePrevAuth(email: String) {
		userSignUpRepositorySupport.deletePrevAuth(email)
	}
}

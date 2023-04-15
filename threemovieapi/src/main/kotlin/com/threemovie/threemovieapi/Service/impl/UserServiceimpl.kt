package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest
import com.threemovie.threemovieapi.Entity.UserInfo
import com.threemovie.threemovieapi.Entity.UserLogin
import com.threemovie.threemovieapi.Repository.Support.UserInfoRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserSignUpRepositorySupport
import com.threemovie.threemovieapi.Repository.UserInfoRepository
import com.threemovie.threemovieapi.Repository.UserLoginRepository
import com.threemovie.threemovieapi.Service.UserService
import com.threemovie.threemovieapi.Utils.RandomKeyString
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceimpl(
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userInfoRepositorySupport: UserInfoRepositorySupport,
	val userSignUpRepositorySupport: UserSignUpRepositorySupport,
	val userLoginRepository: UserLoginRepository,
	val userInfoRepository: UserInfoRepository,
	val emailService: EmailServiceimpl,
	val passwordEncoder: BCryptPasswordEncoder,
) : UserService {
	override fun singnUpAccount(signUpRequest: AccountSignUpRequest) {
		val (email, pass, nickName, sex, birth) = signUpRequest
		val encodePass = passwordEncoder.encode(pass)
		
		val userLogin = UserLogin(email, encodePass)
		val userInfo = UserInfo(email, nickName, sex, birth)
		userLoginRepository.save(userLogin)
		userInfoRepository.save(userInfo)
	}
	
	override fun loginAccount(email: String, pass: String): Boolean {
		val password = passwordEncoder.encode(pass)
		return userLoginRepositorySupport.checkLogin(email, password)
	}
	
	override fun resetPassword(email: String) {
		val pass = RandomKeyString.randomAlphabetNumber(20)
		val password = passwordEncoder.encode(pass)
		emailService.sendMessage(email, "resetpass", pass)
		
		userLoginRepositorySupport.updatePass(email, password)
	}
	
	override fun changePassword(email: String, pass: String) {
		val newPassword = passwordEncoder.encode(pass)
		emailService.sendMessage(email, "changepass", "changepass")
		
		userLoginRepositorySupport.updatePass(email, newPassword)
	}
	
	override fun changeNickName(email: String, nickName: String) {
		userInfoRepositorySupport.updateNickName(email, nickName)
	}
	
	override fun existsEmail(email: String): Boolean {
		return userLoginRepositorySupport.existsEmail(email)
	}
	
	override fun existsNickName(nickName: String): Boolean {
		return userInfoRepositorySupport.existsNickName(nickName)
	}
	
	override fun existsAuth(email: String): Boolean {
		return userSignUpRepositorySupport.existsSuccessCode(email)
	}
	
	override fun checkAuth(email: String, authCode: String): Boolean {
		return userSignUpRepositorySupport.checkAuthCode(email, authCode)
	}
	
	override fun deletePrevAuth(email: String) {
		userSignUpRepositorySupport.deletePrevAuth(email)
	}
}

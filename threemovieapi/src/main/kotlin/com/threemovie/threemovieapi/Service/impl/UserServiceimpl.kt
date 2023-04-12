package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Repository.Support.UserInfoRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserSignUpRepositorySupport
import com.threemovie.threemovieapi.Service.UserService
import com.threemovie.threemovieapi.Utils.RandomKeyString
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceimpl(
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userInfoRepositorySupport: UserInfoRepositorySupport,
	val userSignUpRepositorySupport: UserSignUpRepositorySupport,
	val emailService: EmailServiceimpl
) : UserService {
	override fun singnUpAccount() {
	
	}
	
	override fun resetPassword(email: String) {
		val passwordEncoder = BCryptPasswordEncoder()
		val ePw = RandomKeyString.randomAlphabetNumber(20)
		val password = passwordEncoder.encode(ePw)
		emailService.sendMessage(email, "resetpass", ePw)
		
		userLoginRepositorySupport.updatePass(email, password)
	}
	
	override fun changePassword(email: String, pass: String) {
		val passwordEncoder = BCryptPasswordEncoder()
		val newPassword = passwordEncoder.encode(pass)
		emailService.sendMessage(email, "changepass", "changepass")
		
		userLoginRepositorySupport.updatePass(email, newPassword)
	}
	
	override fun existsEmail(email: String): Boolean {
		return userLoginRepositorySupport.existsEmail(email)
	}
	
	override fun existsNickName(nickName: String): Boolean {
		return userInfoRepositorySupport.existsNickName(nickName)
	}
}

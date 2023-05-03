package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.Request.UpdateUserDataRequest
import com.threemovie.threemovieapi.Entity.UserData
import com.threemovie.threemovieapi.Repository.Support.UserDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Utils.RandomKeyString
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDataService(
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userdataRepositorySupport: UserDataRepositorySupport,
	val emailService: EmailService,
	val passwordEncoder: BCryptPasswordEncoder,
) {
	
	fun updateUserdata(userDataRequest: UpdateUserDataRequest): Boolean {
		userdataRepositorySupport.updateUserData(userDataRequest)
		
		return true
	}
	
	fun getUserData(email: String): UserData {
		return userdataRepositorySupport.getUserData(email)
	}
	
	fun getNickName(email: String): String {
		return userdataRepositorySupport.getNickName(email)
	}
	
	fun resetPassword(email: String) {
		val pass = RandomKeyString.randomAlphabetNumber(20)
		val password = passwordEncoder.encode(pass)
		emailService.sendMessage(email, "resetpass", pass)
		
		userLoginRepositorySupport.updatePass(email, password)
	}
	
	fun changePassword(email: String, pass: String) {
		val newPassword = passwordEncoder.encode(pass)
		emailService.sendMessage(email, "changepass", "changepass")
		
		userLoginRepositorySupport.updatePass(email, newPassword)
	}
	
	fun existsEmail(email: String): Boolean {
		return userLoginRepositorySupport.existsEmail(email)
	}
	
	fun existsNickName(nickName: String): Boolean {
		return userdataRepositorySupport.existsNickName(nickName)
	}
}

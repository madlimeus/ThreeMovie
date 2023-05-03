package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Entity.DTO.Request.UpdateUserDataRequest
import com.threemovie.threemovieapi.Entity.UserData
import com.threemovie.threemovieapi.Repository.Support.UserDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Service.UserDataService
import com.threemovie.threemovieapi.Utils.RandomKeyString
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserDataServiceimpl(
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userdataRepositorySupport: UserDataRepositorySupport,
	val emailService: EmailServiceimpl,
	val passwordEncoder: BCryptPasswordEncoder,
) : UserDataService {
	
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
	
	override fun existsEmail(email: String): Boolean {
		return userLoginRepositorySupport.existsEmail(email)
	}
	
	override fun existsNickName(nickName: String): Boolean {
		return userdataRepositorySupport.existsNickName(nickName)
	}
}

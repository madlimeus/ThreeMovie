package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.UserData
import com.threemovie.threemovieapi.Repository.Support.UserDataRepositorySupport
import com.threemovie.threemovieapi.Repository.Support.UserLoginRepositorySupport
import com.threemovie.threemovieapi.Utils.RandomKeyString
import com.threemovie.threemovieapi.exception.AccountNotFoundException
import com.threemovie.threemovieapi.exception.AlreadyExistEmailException
import com.threemovie.threemovieapi.exception.AlreadyExistNickNameException
import com.threemovie.threemovieapi.exception.DataNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class UserDataService(
	val userLoginRepositorySupport: UserLoginRepositorySupport,
	val userDataRepositorySupport: UserDataRepositorySupport,
	val emailService: EmailService,
	val passwordEncoder: BCryptPasswordEncoder,
) {
	
	fun updateUserdata(email: String, nickName: String, sex: Boolean?, birth: LocalDate?): Boolean {
		if (userDataRepositorySupport.existsNickName(nickName))
			throw AlreadyExistNickNameException()
		if (! userDataRepositorySupport.existsEmail(email))
			throw AccountNotFoundException()
		userDataRepositorySupport.updateUserData(email, nickName, sex, birth)
		
		return true
	}
	
	fun getUserData(email: String): UserData {
		return userDataRepositorySupport.getUserData(email) ?: throw DataNotFoundException()
	}
	
	fun getNickName(email: String): String {
		return userDataRepositorySupport.getNickName(email) ?: throw DataNotFoundException()
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
	
	fun existsEmail(email: String) {
		if (! userLoginRepositorySupport.existsEmail(email))
			throw AlreadyExistEmailException()
	}
	
	fun existsNickName(nickName: String) {
		if (! userDataRepositorySupport.existsNickName(nickName))
			throw AlreadyExistNickNameException()
	}
}

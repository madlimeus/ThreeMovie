package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest

interface UserService {
	
	fun singnUpAccount(signUpRequest: AccountSignUpRequest)
	
	fun loginAccount(email: String, pass: String): Boolean
	
	fun resetPassword(email: String)
	
	fun changePassword(email: String, pass: String)
	
	fun changeNickName(email: String, nickName: String)
	
	fun existsEmail(email: String): Boolean
	
	fun existsNickName(nickName: String): Boolean
	
	fun existsAuth(email: String): Boolean
	
	fun checkAuth(email: String, authCode: String): Boolean
	
	fun deletePrevAuth(email: String)
}

package com.threemovie.threemovieapi.Service

interface UserService {
	
	fun singnUpAccount()
	
	fun resetPassword(email: String)
	
	fun changePassword(email: String, pass: String)
	
	fun existsEmail(email: String): Boolean
	
	fun existsNickName(nickName: String): Boolean
}

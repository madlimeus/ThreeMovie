package com.threemovie.threemovieapi.Service

import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest

interface UserAuthService {
	fun signUpAccount(signUpRequest: AccountSignUpRequest)
	
	fun signOutAccount(accessToken: String)
	
	fun loginAccount(email: String, pass: String): Boolean
	
	fun logoutAccount(accessToken: String): Boolean
	
	fun existsAuth(email: String): Boolean
	
	fun checkAuth(email: String, authCode: String): Boolean
	
	fun updateAuth(email: String)
	
	fun deletePrevAuth(email: String)
	
	fun reissue(refreshToken: String): Boolean
}

package com.threemovie.threemovieapi.domain.user.controller

import com.threemovie.threemovieapi.domain.user.controller.request.AccountSignUpRequest
import com.threemovie.threemovieapi.domain.user.controller.request.AuthRequest
import com.threemovie.threemovieapi.domain.user.controller.request.EmailRequest
import com.threemovie.threemovieapi.domain.user.controller.request.LoginRequest
import com.threemovie.threemovieapi.domain.user.service.EmailService
import com.threemovie.threemovieapi.domain.user.service.UserAuthService
import com.threemovie.threemovieapi.domain.user.service.UserDataService
import com.threemovie.threemovieapi.global.security.service.JwtTokenProvider
import com.threemovie.threemovieapi.global.security.service.RedisUtil
import com.threemovie.threemovieapi.domain.user.exception.AlreadyExistEmailException
import com.threemovie.threemovieapi.global.security.config.UserRole
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "UserAuthController", description = "인증 관련 컨트롤러")
@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/auth")
class UserAuthController(
	val emailService: EmailService,
	val userAuthService: UserAuthService,
	val userDataService: UserDataService,
	val jwtTokenProvider: JwtTokenProvider,
	val redisUtil: RedisUtil
) {
	
	@PostMapping("/mail")
	fun sendAuthEmail(@RequestBody emailRequest: EmailRequest): ResponseEntity<String> {
		val (email, isSignUp) = emailRequest
		userDataService.existsEmail(email)
		if (isSignUp)
			throw AlreadyExistEmailException()
		emailService.sendAuthMail(email)
		return ResponseEntity.status(HttpStatus.OK).body("success")
	}
	
	@PostMapping("/check/code")
	fun checkAuthCode(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
		val (email, authCode) = authRequest
		userAuthService.checkAuth(email, authCode)
		
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 인증 되었습니다.")
	}
	
	@PostMapping("/login")
	fun loginAccount(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
		val (email, pass) = loginRequest
		
		userDataService.existsEmail(email)
		userAuthService.loginAccount(email, pass)
		
		val nickName = userDataService.getNickName(email)
		val userRole = UserRole.USER.toString()
		val retToken = jwtTokenProvider.createAllToken(email, userRole, nickName)
		
		redisUtil.setDataExpire(email, retToken.refreshToken, jwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME.toLong())
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(retToken)
	}
	
	@PostMapping("/logout")
	fun logoutAccount(request: HttpServletRequest): ResponseEntity<String> {
		val accessToken = request.getHeader("Authorization").substring(7)
		
		userAuthService.logoutAccount(accessToken)
		
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 로그아웃 됐습니다.")
	}
	
	@PostMapping("/signup")
	fun signUpAccount(@RequestBody account: AccountSignUpRequest): ResponseEntity<String> {
		userAuthService.existsAuth(account.email)
		
		userDataService.existsEmail(account.email)
		userDataService.existsNickName(account.nickName)
		
		
		userAuthService.signUpAccount(account)
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 가입 되었습니다.")
	}
	
	@PostMapping("/signout")
	fun signOutAccount(request: HttpServletRequest): ResponseEntity<String> {
		val accessToken = request.getHeader("Authorization").substring(7)
		userAuthService.signOutAccount(accessToken)
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 탈퇴 되었습니다.")
	}
	
	@PostMapping("/reissue")
	fun reissue(request: HttpServletRequest): ResponseEntity<String> {
		val refreshToken = request.getHeader("Authorization").substring(7)
		
		userAuthService.reissue(refreshToken)
		
		val userRole = UserRole.USER.toString()
		val email = jwtTokenProvider.getEmail(refreshToken)
		val accessToken = jwtTokenProvider.generateAccessToken(email, userRole)
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(accessToken)
	}
	
	@PostMapping("/password/change")
	fun changePassword(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
		val (email, pass) = loginRequest
		userDataService.changePassword(email, pass)
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경 되었습니다. 다시 로그인 해주시기 바랍니다.")
	}
}

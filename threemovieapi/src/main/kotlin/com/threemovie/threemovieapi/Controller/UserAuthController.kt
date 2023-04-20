package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Config.UserRole
import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.AuthRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.EmailRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.LoginRequest
import com.threemovie.threemovieapi.Service.impl.EmailServiceimpl
import com.threemovie.threemovieapi.Service.impl.UserAuthServiceimpl
import com.threemovie.threemovieapi.Service.impl.UserInfoServiceimpl
import com.threemovie.threemovieapi.Utils.jwt.JwtTokenProvider
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
	val emailService: EmailServiceimpl,
	val userAuthService: UserAuthServiceimpl,
	val userInfoService: UserInfoServiceimpl,
	val jwtTokenProvider: JwtTokenProvider
) {
	
	@PostMapping("/mail")
	fun sendAuthEmail(@RequestBody emailRequest: EmailRequest): ResponseEntity<String> {
		val (email, isSignUp) = emailRequest
		println(emailRequest)
		val ret = userInfoService.existsEmail(email)
		if (ret && isSignUp)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 이메일 입니다.")
		emailService.sendAuthMail(email)
		return ResponseEntity.status(HttpStatus.OK).body("success")
	}
	
	@PostMapping("/check/code")
	fun checkAuthCode(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
		val (email, authCode) = authRequest
		val ret = userAuthService.checkAuth(email, authCode)
		if (! ret) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("올바른 코드를 입력 해주세요.")
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 인증 되었습니다.")
	}
	
	@PostMapping("/login")
	fun loginAccount(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
		val (email, pass) = loginRequest
		var ret = userInfoService.existsEmail(email)
		if (! ret)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("가입되지 않은 메일이거나 비밀번호가 틀렸습니다.")
		
		ret = userAuthService.loginAccount(email, pass)
		
		if (! ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("가입되지 않은 메일이거나 비밀번호가 틀렸습니다.")
		}
		val nickName = userInfoService.getNickName(email)
		val userRole = UserRole.USER.toString()
		val retToken = jwtTokenProvider.createAllToken(email, userRole, nickName)
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(retToken)
	}
	
	@PostMapping("/logout")
	fun logoutAccount(request: HttpServletRequest): ResponseEntity<String> {
		val accessToken = request.getHeader("Authorization").substring(7)
		
		val ret = userAuthService.logoutAccount(accessToken)
		if (ret) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 로그아웃 됐습니다.")
	}
	
	@PostMapping("/signup")
	fun signUpAccount(@RequestBody account: AccountSignUpRequest): ResponseEntity<String> {
		var ret = userAuthService.existsAuth(account.email)
		if (! ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 인증을 진행 해주세요.")
		}
		
		ret = userInfoService.existsEmail(account.email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 가입 된 이메일 입니다.")
		}
		
		ret = userInfoService.existsNickName(account.nickName)
		if (ret) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 존재하는 별명입니다.")
		}
		
		userAuthService.signUpAccount(account)
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 가입 되었습니다.")
	}
	
	@PostMapping("/signout")
	fun signOutAccount(request: HttpServletRequest): ResponseEntity<String> {
		println(request.getHeader("Authorization"))
		val accessToken = request.getHeader("Authorization").substring(7)
		userAuthService.signOutAccount(accessToken)
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 탈퇴 되었습니다.")
	}
	
	@PostMapping("/reissue")
	fun reissue(request: HttpServletRequest): ResponseEntity<String> {
		println(request.cookies)
		val refreshToken = request.getHeader("Authorization").substring(7)
		if (refreshToken.isNullOrEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청 입니다.")
		val ret = userAuthService.reissue(refreshToken)
		if (! ret)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청 입니다.")
		
		val userRole = UserRole.USER.toString()
		val email = jwtTokenProvider.getEmail(refreshToken)
		val accessToken = jwtTokenProvider.generateAccessToken(email, userRole)
		
		return ResponseEntity.status(HttpStatus.OK)
			.body(accessToken)
	}
	
	@PostMapping("/password/change")
	fun changePassword(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
		val (email, pass) = loginRequest
		userInfoService.changePassword(email, pass)
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경 되었습니다. 다시 로그인 해주시기 바랍니다.")
	}
}

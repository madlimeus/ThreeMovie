package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest
import com.threemovie.threemovieapi.Service.impl.UserServiceimpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "UserController", description = "인증 메일 혹은 알림 메일 보내는 컨트롤러")
@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserServiceimpl) {
	
	@PostMapping("/mail/exists")
	fun checkDuplicatedEmail(@RequestBody email: String): ResponseEntity<String> {
		val ret = userService.existsEmail(email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입 된 이메일 입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 이메일 입니다.")
	}
	
	@PostMapping("/auth")
	fun checkAuthCode(@RequestBody email: String, @RequestBody authCode: String): ResponseEntity<String> {
		val ret = userService.loginAccount(email, authCode)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("올바른 코드를 입력 해주세요.")
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 인증 되었습니다.")
	}
	
	@PostMapping("/login")
	fun loginAccount(@RequestBody email: String, @RequestBody pass: String): ResponseEntity<String> {
		val ret = userService.loginAccount(email, pass)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("로그인에 실패 하였습니다.")
		}
		
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 가입 되었습니다.")
	}
	
	@PostMapping("/signup")
	fun signUpAccount(@RequestBody account: AccountSignUpRequest): ResponseEntity<String> {
		var ret = userService.existsAuth(account.email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 인증을 진행 해주세요.")
		}
		
		ret = userService.existsEmail(account.email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입 된 이메일 입니다.")
		}
		
		ret = userService.existsNickName(account.nickName)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 별명입니다.")
		}
		
		userService.singnUpAccount(account)
		
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 가입 되었습니다.")
	}
	
	@PostMapping("/password/reset")
	fun resetPassword(@RequestBody email: String): ResponseEntity<String> {
		var ret = userService.existsAuth(email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 인증을 진행 해주세요.")
		}
		
		userService.resetPassword(email)
		return ResponseEntity.status(HttpStatus.OK).body("임시 비밀번호가 전송 되었습니다.")
	}
	
	@PostMapping("/password/change")
	fun changePassword(@RequestBody email: String, @RequestBody pass: String): ResponseEntity<String> {
		userService.changePassword(email, pass)
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경 되었습니다. 다시 로그인 해주시기 바랍니다.")
	}
	
	@PostMapping("/nickname/exists")
	fun checkDuplicatedNickName(@RequestBody nickName: String): ResponseEntity<String> {
		val ret = userService.existsNickName(nickName)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임 입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임 입니다.")
	}
	
	@PostMapping("/nickname/change")
	fun changeNickName(@RequestBody email: String, @RequestBody nickName: String): ResponseEntity<String> {
		userService.changeNickName(email, nickName)
		
		return ResponseEntity.status(HttpStatus.OK).body("닉네임이 변경 되었습니다.")
	}
	
}

package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.DTO.Request.AccountSignUpRequest
import com.threemovie.threemovieapi.Service.impl.UserServiceimpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "UserController", description = "인증 메일 혹은 알림 메일 보내는 컨트롤러")
@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserServiceimpl) {
	
	@PostMapping("/mail/exists")
	fun checkDuplicatedEmail(email: String): ResponseEntity<String> {
		val ret = userService.existsEmail(email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입 된 이메일 입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 이메일 입니다.")
	}
	
	@PostMapping("/nickname/exists")
	fun checkDuplicatedNickName(nickName: String): ResponseEntity<String> {
		val ret = userService.existsNickName(nickName)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 닉네임 입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임 입니다.")
	}
	
	@PostMapping("/signup")
	fun signUpAccount(account: AccountSignUpRequest): ResponseEntity<String> {
		return ResponseEntity.status(HttpStatus.OK).body("성공적으로 가입 되었습니다.")
	}
	
	@PostMapping("/password/reset")
	fun resetPassword(email: String): ResponseEntity<String> {
		return ResponseEntity.status(HttpStatus.OK).body("임시 비밀번호가 전송 되었습니다..")
	}
}

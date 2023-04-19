package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Entity.DTO.Request.ChangeNickNameRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.EmailRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.LoginRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.NickNameRequest
import com.threemovie.threemovieapi.Service.impl.UserAuthServiceimpl
import com.threemovie.threemovieapi.Service.impl.UserInfoServiceimpl
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
class UserInfoController(
	val userInfoService: UserInfoServiceimpl,
	val userAuthService: UserAuthServiceimpl
) {
	
	@PostMapping("/password/reset")
	fun resetPassword(@RequestBody emailRequest: EmailRequest): ResponseEntity<String> {
		val (email) = emailRequest
		var ret = userAuthService.existsAuth(email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 인증을 진행 해주세요.")
		}
		
		userInfoService.resetPassword(email)
		return ResponseEntity.status(HttpStatus.OK).body("임시 비밀번호가 전송 되었습니다.")
	}
	
	@PostMapping("/password/change")
	fun changePassword(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
		val (email, pass) = loginRequest
		userInfoService.changePassword(email, pass)
		return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 변경 되었습니다. 다시 로그인 해주시기 바랍니다.")
	}
	
	@PostMapping("/nickname/exists")
	fun checkDuplicatedNickName(@RequestBody nickNameRequest: NickNameRequest): ResponseEntity<String> {
		val (nickName) = nickNameRequest
		val ret = userInfoService.existsNickName(nickName)
		if (ret) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 존재하는 닉네임 입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임 입니다.")
	}
	
	@PostMapping("/nickname/change")
	fun changeNickName(@RequestBody changeNickNameRequest: ChangeNickNameRequest): ResponseEntity<String> {
		val (email, nickName) = changeNickNameRequest
		userInfoService.changeNickName(email, nickName)
		
		return ResponseEntity.status(HttpStatus.OK).body("닉네임이 변경 되었습니다.")
	}
	
}

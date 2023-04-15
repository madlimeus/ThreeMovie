package com.threemovie.threemovieapi.controller

import com.threemovie.threemovieapi.Service.impl.EmailServiceimpl
import com.threemovie.threemovieapi.Service.impl.UserServiceimpl
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "MailController", description = "인증 메일 혹은 알림 메일 보내는 컨트롤러")
@RestController
@RequestMapping("/api/mail")
class MailController(val emailService: EmailServiceimpl, val userService: UserServiceimpl) {
	
	@PostMapping("/auth")
	fun sendAuthEmail(@RequestBody email: String): ResponseEntity<String> {
		val ret = userService.existsEmail(email)
		if (ret)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 가입된 이메일 입니다.")
		emailService.sendAuthMail(email)
		return ResponseEntity.status(HttpStatus.OK).body("success")
	}
}

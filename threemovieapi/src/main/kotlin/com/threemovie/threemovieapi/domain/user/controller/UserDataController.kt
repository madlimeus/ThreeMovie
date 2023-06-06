package com.threemovie.threemovieapi.domain.user.controller

import com.threemovie.threemovieapi.domain.user.controller.request.EmailRequest
import com.threemovie.threemovieapi.domain.user.controller.request.NickNameRequest
import com.threemovie.threemovieapi.domain.user.controller.request.UpdateUserDataRequest
import com.threemovie.threemovieapi.domain.user.entity.dto.UserDataDTO
import com.threemovie.threemovieapi.domain.user.service.UserAuthService
import com.threemovie.threemovieapi.domain.user.service.UserDataService
import com.threemovie.threemovieapi.global.security.service.JwtTokenProvider
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "UserController", description = "인증 메일 혹은 알림 메일 보내는 컨트롤러")
@RestController
@RequestMapping("/api/user")
class UserDataController(
	val userDataService: UserDataService,
	val userAuthService: UserAuthService,
	val jwtTokenProvider: JwtTokenProvider
) {
	
	@PostMapping("/reset/password")
	fun resetPassword(@RequestBody emailRequest: EmailRequest): ResponseEntity<String> {
		val (email) = emailRequest
		var ret = userAuthService.existsAuth(email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 인증을 진행 해주세요.")
		}
		
		userDataService.resetPassword(email)
		return ResponseEntity.ok("임시 비밀번호가 전송 되었습니다.")
	}
	
	@PostMapping("/exist/nickname")
	fun checkDuplicatedNickName(@RequestBody nickNameRequest: NickNameRequest): ResponseEntity<String> {
		val (nickName) = nickNameRequest
		userDataService.existsNickName(nickName)
		
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임 입니다.")
	}
	
	@GetMapping("/data")
	fun getUserData(request: HttpServletRequest): ResponseEntity<UserDataDTO> {
		val accessToken = request.getHeader("Authorization").substring(7)
		val email = jwtTokenProvider.getEmail(accessToken)
		val res = userDataService.getUserData(email)
		
		return ResponseEntity.status(HttpStatus.OK).body(res)
	}
	
	@PatchMapping("/data")
	fun updateUserData(@RequestBody updateUserdataRequest: UpdateUserDataRequest): ResponseEntity<String> {
		val (email, nickName, sex, birth) = updateUserdataRequest
		userDataService.updateUserdata(email, nickName, sex, birth)
		
		return ResponseEntity.status(HttpStatus.OK).body("정보가 변경 되었습니다.")
	}
}

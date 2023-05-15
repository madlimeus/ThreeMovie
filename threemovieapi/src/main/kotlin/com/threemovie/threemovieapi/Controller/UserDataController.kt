package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Entity.DTO.Request.EmailRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.NickNameRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.UpdateUserDataRequest
import com.threemovie.threemovieapi.Entity.UserData
import com.threemovie.threemovieapi.Service.UserAuthService
import com.threemovie.threemovieapi.Service.UserDataService
import com.threemovie.threemovieapi.Utils.jwt.JwtTokenProvider
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
	
	@PostMapping("/password/reset")
	fun resetPassword(@RequestBody emailRequest: EmailRequest): ResponseEntity<String> {
		val (email) = emailRequest
		var ret = userAuthService.existsAuth(email)
		if (ret) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("이메일 인증을 진행 해주세요.")
		}
		
		userDataService.resetPassword(email)
		return ResponseEntity.ok("임시 비밀번호가 전송 되었습니다.")
	}
	
	@PostMapping("/nickname/exists")
	fun checkDuplicatedNickName(@RequestBody nickNameRequest: NickNameRequest): ResponseEntity<String> {
		val (nickName) = nickNameRequest
		userDataService.existsNickName(nickName)
		
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임 입니다.")
	}
	
	@PostMapping("/mypage")
	fun getUserData(request: HttpServletRequest): ResponseEntity<UserData> {
		val accessToken = request.getHeader("Authorization").substring(7)
		val email = jwtTokenProvider.getEmail(accessToken)
		val res = userDataService.getUserData(email)
		
		return ResponseEntity.status(HttpStatus.OK).body(res)
	}
	
	@PatchMapping("/data/change")
	fun updateUserData(@RequestBody updateUserdataRequest: UpdateUserDataRequest): ResponseEntity<String> {
		val (email, nickName, sex, birth) = updateUserdataRequest
		userDataService.updateUserdata(email, nickName, sex, birth)
		
		return ResponseEntity.status(HttpStatus.OK).body("정보가 변경 되었습니다.")
	}
}

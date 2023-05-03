package com.threemovie.threemovieapi.Controller

import com.threemovie.threemovieapi.Entity.DTO.Request.EmailRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.NickNameRequest
import com.threemovie.threemovieapi.Entity.DTO.Request.UpdateUserDataRequest
import com.threemovie.threemovieapi.Entity.UserData
import com.threemovie.threemovieapi.Service.impl.UserAuthServiceimpl
import com.threemovie.threemovieapi.Service.impl.UserDataServiceimpl
import com.threemovie.threemovieapi.Utils.jwt.JwtTokenProvider
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "UserController", description = "인증 메일 혹은 알림 메일 보내는 컨트롤러")
@RestController
@RequestMapping("/api/user")
class UserDataController(
	val userDataService: UserDataServiceimpl,
	val userAuthService: UserAuthServiceimpl,
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
		return ResponseEntity.status(HttpStatus.OK).body("임시 비밀번호가 전송 되었습니다.")
	}
	
	@PostMapping("/nickname/exists")
	fun checkDuplicatedNickName(@RequestBody nickNameRequest: NickNameRequest): ResponseEntity<String> {
		val (nickName) = nickNameRequest
		val ret = userDataService.existsNickName(nickName)
		if (ret) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이미 존재하는 닉네임 입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("사용가능한 닉네임 입니다.")
	}
	
	@PostMapping("/mypage")
	fun getUserData(request: HttpServletRequest): ResponseEntity<UserData> {
		println(request)
		val accessToken = request.getHeader("Authorization").substring(7)
		val email = jwtTokenProvider.getEmail(accessToken)
		val res = userDataService.getUserData(email)
		
		return ResponseEntity.status(HttpStatus.OK).body(res)
	}
	
	@PostMapping("/data/change")
	fun updatedata(@RequestBody updateUserdataRequest: UpdateUserDataRequest): ResponseEntity<String> {
		val ret = userDataService.updateUserdata(updateUserdataRequest)
		if (! ret) {
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.")
		}
		return ResponseEntity.status(HttpStatus.OK).body("정보가 변경 되었습니다.")
	}
}

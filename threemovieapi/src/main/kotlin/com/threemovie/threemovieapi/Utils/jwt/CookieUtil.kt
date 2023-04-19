package com.threemovie.threemovieapi.Utils.jwt

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service

@Service
class CookieUtil(val jwtTokenProvider: JwtTokenProvider) {
	fun createCookie(cookieName: String, value: String): ResponseCookie {
		val token = ResponseCookie.from(cookieName, value)
			.httpOnly(true)
			.maxAge(if (cookieName == jwtTokenProvider.ACCESS_TOKEN_NAME) jwtTokenProvider.ACCESS_TOKEN_EXPIRE_TIME.toLong() else jwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME.toLong())
			.secure(true)
			.path("/")
			.sameSite("None")
			.build()
		
		return token
	}
	
	fun getCookie(req: HttpServletRequest, cookieName: String): Cookie? {
		val cookies = req.cookies ?: return null
		for (cookie in cookies) {
			if (cookie.name.equals(cookieName)) return cookie
		}
		return null
	}
}

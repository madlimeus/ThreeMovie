package com.threemovie.threemovieapi.Utils.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
	private val jwtTokenProvider: JwtTokenProvider,
	private val redisUtil: RedisUtil
) : OncePerRequestFilter() {
	
	override fun doFilterInternal(
		request: HttpServletRequest,
		response: HttpServletResponse,
		filterChain: FilterChain
	) {
		var accessToken = request.getHeader("Authorization")
		if (accessToken != null)
			accessToken = accessToken.substring(7)
		
		checkAccessToken(accessToken)
		filterChain.doFilter(request, response)
	}
	
	private fun checkAccessToken(accessToken: String?): Boolean {
		val isLogout = redisUtil.getData(accessToken.toString())
		
		if (! accessToken.isNullOrEmpty() && isLogout == "null") {
			try {
				if (jwtTokenProvider.validateToken(accessToken)) {
					val authentication = jwtTokenProvider.getAuthentication(accessToken)
					SecurityContextHolder.getContext().authentication = authentication
					return true
				}
			} catch (e: Exception) {
				logger.info(e)
				return false
			}
		}
		return false
	}
	
}

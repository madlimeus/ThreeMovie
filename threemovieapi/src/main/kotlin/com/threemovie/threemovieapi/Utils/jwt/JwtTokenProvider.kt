package com.threemovie.threemovieapi.Utils.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import jakarta.annotation.PostConstruct
import org.hibernate.query.sqm.tree.SqmNode.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtTokenProvider(
	@Value("\${jwt.secret.key}")
	val secretKey: String,
	@Value("\${jwt.secret.refresh-token-validity-in-seconds}")
	val REFRESH_TOKEN_EXPIRE_TIME: Int,
	@Value("\${jwt.secret.access-token-validity-in-seconds}")
	val ACCESS_TOKEN_EXPIRE_TIME: Int
) {
	private lateinit var key: Key
	private final val signatureAlgorithm = SignatureAlgorithm.HS256
	final val ACCESS_TOKEN_NAME = "AccessToken"
	final val REFRESH_TOKEN_NAME = "RefreshToken"
	
	@PostConstruct
	fun init() {
		val bytes = Base64.getDecoder().decode(secretKey)
		key = Keys.hmacShaKeyFor(bytes)
	}
	
	fun extractAllClaims(token: String): Claims {
		val ret = Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.body
		println(ret)
		return ret
	}
	
	fun getEmail(token: String): String {
		return extractAllClaims(token).get("sub", String::class.java)
	}
	
	fun getExpiration(token: String): Long {
		val expiration = extractAllClaims(token).expiration.time
		val now = Date().time
		return (expiration - now) / 1000
	}
	
	fun getUserRole(token: String): String {
		return extractAllClaims(token).get("role", String::class.java)
	}
	
	fun getAuthentication(token: String): Authentication {
		val userDetails = User.builder()
			.username(getEmail(token))
			.password("")
			.roles(getUserRole(token))
			.build()
		return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
	}
	
	fun createAllToken(email: String, userRole: String, nickName: String): TokenResponse {
		return TokenResponse(
			generateAccessToken(email, userRole),
			generateRefreshToken(email),
			nickName
		)
	}
	
	fun generateAccessToken(email: String, userRole: String): String {
		val now = LocalDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRE_TIME.toLong())
		println(now)
		val accessToken = Jwts.builder()
			.setSubject(email)
			.claim("role", userRole)
			.setExpiration(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
			.signWith(key, signatureAlgorithm)
			.compact()
		
		return accessToken
	}
	
	fun generateRefreshToken(email: String): String {
		val now = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRE_TIME.toLong())
		val refreshToken = Jwts.builder()
			.setSubject(email)
			.setExpiration(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
			.signWith(key, signatureAlgorithm)
			.compact()
		
		return refreshToken
	}
	
	fun validateToken(token: String): Boolean {
		try {
			extractAllClaims(token)
			return true
		} catch (e: SecurityException) {
			log.info("Invalid JWT Token", e)
			throw IllegalArgumentException("잘못된 토큰 입니다.")
		} catch (e: MalformedJwtException) {
			log.info("Invalid JWT Token", e)
			throw IllegalArgumentException("잘못된 토큰 입니다.")
		} catch (e: ExpiredJwtException) {
			log.info("Expired JWT Token", e)
			throw IllegalArgumentException("만료된 토큰 입니다.")
		} catch (e: UnsupportedJwtException) {
			log.info("Unsupported JWT Token", e)
			throw IllegalArgumentException("지원하지 않는 토큰 입니다.")
		} catch (e: IllegalArgumentException) {
			log.info("JWT claims string is empty.", e)
			throw IllegalArgumentException("토큰이 비어 있습니다.")
		}
		return false
	}
}

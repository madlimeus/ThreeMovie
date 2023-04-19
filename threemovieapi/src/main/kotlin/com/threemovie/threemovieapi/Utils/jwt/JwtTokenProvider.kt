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
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.body
	}
	
	fun getEmail(token: String): String {
		return extractAllClaims(token).get("email", String::class.java)
	}
	
	fun getExpiration(token: String): Long {
		val expiration = extractAllClaims(token).expiration.time
		val now = Date().time
		return expiration - now
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
	
	fun createAllToken(email: String, userRole: String): TokenResponse {
		return TokenResponse(
			generateAccessToken(email, userRole),
			generateRefreshToken(email)
		)
	}
	
	fun generateAccessToken(email: String, userRole: String): String {
		val now = Date().time
		val accessToken = Jwts.builder()
			.setSubject(email)
			.claim("role", userRole)
			.setExpiration(Date(now + ACCESS_TOKEN_EXPIRE_TIME))
			.signWith(key, signatureAlgorithm)
			.compact()
		
		return accessToken
	}
	
	fun generateRefreshToken(email: String): String {
		val now = Date().time
		val accessToken = Jwts.builder()
			.setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
			.signWith(key, signatureAlgorithm)
			.compact()
		
		return accessToken
	}
	
	fun validateToken(token: String): Boolean {
		try {
			extractAllClaims(token)
			return true
		} catch (e: SecurityException) {
			log.info("Invalid JWT Token", e)
		} catch (e: MalformedJwtException) {
			log.info("Invalid JWT Token", e)
		} catch (e: ExpiredJwtException) {
			log.info("Expired JWT Token", e)
		} catch (e: UnsupportedJwtException) {
			log.info("Unsupported JWT Token", e)
		} catch (e: IllegalArgumentException) {
			log.info("JWT claims string is empty.", e)
		}
		return false
	}
}

package com.threemovie.threemovieapi.config

import com.threemovie.threemovieapi.Utils.jwt.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@ComponentScan(basePackages = ["com.threemovie.threemovieapi.Utils.jwt"])
@EnableWebSecurity
@Configuration
class SecurityConfig(
	val jwtAuthFilter: JwtAuthFilter
) {
	
	@Bean
	fun passwordEncoder(): BCryptPasswordEncoder {
		// 비밀번호를 DB에 저장하기 전 사용할 암호화
		return BCryptPasswordEncoder()
	}
	
	@Bean
	fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
		// 인터셉터로 요청을 안전하게 보호하는 방법 설정
		http // jwt 토큰 사용을 위한 설정
			.csrf().disable()
			.httpBasic().disable()
			.formLogin().disable()
			.addFilterBefore(
				jwtAuthFilter,
				UsernamePasswordAuthenticationFilter::class.java
			)
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 예외 처리
			.and()
			.authorizeHttpRequests()
			.requestMatchers("/api/user/mypage/**").authenticated() // 마이페이지 인증 필요
			.requestMatchers("/**").permitAll()
			.anyRequest().permitAll()
			.and()
			.headers()
			.frameOptions().sameOrigin()
			.and()
			.cors()
		
		return http.build()
	}
	
}

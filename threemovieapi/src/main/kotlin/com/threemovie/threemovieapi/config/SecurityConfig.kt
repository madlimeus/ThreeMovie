package com.threemovie.threemovieapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig {
	
	@Bean
	@Throws(Exception::class)
	protected fun config(http: HttpSecurity): SecurityFilterChain? {
		http.cors().disable()
			.csrf().disable()
			.formLogin().disable()
			.headers().frameOptions().disable()
		return http.build()
	}
}

package com.threemovie.threemovieapi.global.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:3000", "http://localhost:8080", "https://moviethree.synology.me")
			.allowedOriginPatterns("*")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS")
			.allowCredentials(true)
	}
}

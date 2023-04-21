package com.threemovie.threemovieapi.Config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:3000", "http://192.168.219.157:3000")
			.allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS")
			.allowCredentials(true)
	}
}

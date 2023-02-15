package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.service.WebClientService
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

class WebClientServiceImpl(
	val webClient: WebClient
): WebClientService {
	override fun getApiData(url: String, path: String): List<Any> {
		val list = webClient
			.get()
			.uri(UriComponentsBuilder
				.fromHttpUrl(url)
				.path(path)
				.build()
				.toUri())
			.retrieve()
			.onStatus(HttpStatus::is4xxClientError) { Mono.error(RuntimeException("4XX Error ${it.statusCode()}")) }
			.onStatus(HttpStatus::is5xxServerError) { Mono.error(RuntimeException("5XX Error ${it.statusCode()}")) }
	}
}

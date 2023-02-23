package com.threemovie.threemovieapi.service.impl

import com.threemovie.threemovieapi.service.WebClientService
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder

@Service
class WebClientServiceImpl(
	val webClient: WebClient
): WebClientService {
	override fun getApiData(url: String, path: String, classType: ParameterizedTypeReference<List<*>>): List<*>? {
		val list = webClient
				.get()
				.uri(
					UriComponentsBuilder
						.fromHttpUrl(url)
						.path(path)
						.build()
						.toUri()
				)
				.retrieve()
				.bodyToMono(classType)
				.block()

		return list
	}
}

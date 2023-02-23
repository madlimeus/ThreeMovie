package com.threemovie.threemovieapi.service

import org.springframework.core.ParameterizedTypeReference

interface WebClientService {

	fun getApiData(url: String, path: String, classType: ParameterizedTypeReference<List<*>>): List<*>?
}

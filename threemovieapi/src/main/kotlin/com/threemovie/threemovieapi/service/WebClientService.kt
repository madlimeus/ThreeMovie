package com.threemovie.threemovieapi.service

interface WebClientService {

	fun getApiData(url: String, path: String): List<Any>
}

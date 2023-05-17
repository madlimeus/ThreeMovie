package com.threemovie.threemovieapi.global.service

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class GET_DATA_USE_DAUM_API {
	companion object {
		fun GET_DATA_USE_DAUM_API(url: String): String {
			val client = HttpClient.newBuilder().build()
			val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
			val response = client.send(request, HttpResponse.BodyHandlers.ofString())
			return response.body()
		}
	}
}

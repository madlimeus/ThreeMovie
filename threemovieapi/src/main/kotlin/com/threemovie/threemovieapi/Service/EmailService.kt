package com.threemovie.threemovieapi.Service

interface EmailService {
	fun sendMessage(to: String, messageType: String, ePw: String)
}

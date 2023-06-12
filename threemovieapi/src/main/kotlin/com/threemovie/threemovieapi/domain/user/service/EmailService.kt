package com.threemovie.threemovieapi.domain.user.service

import jakarta.mail.internet.MimeMessage

interface EmailService {
	fun createMessage(to: String, pass: String?): MimeMessage
	
	fun sendMessage(to: String, pass: String?)
}

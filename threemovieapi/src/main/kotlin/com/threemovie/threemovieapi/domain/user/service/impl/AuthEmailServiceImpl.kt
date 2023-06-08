package com.threemovie.threemovieapi.domain.user.service.impl

import com.threemovie.threemovieapi.domain.user.service.EmailService
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
@EnableAutoConfiguration
class AuthEmailServiceImpl(
	val emailSender: JavaMailSender,
) : EmailService {
	override fun createMessage(to: String, pass: String?): MimeMessage {
		val message = emailSender.createMimeMessage()
		
		message.addRecipients(Message.RecipientType.TO, to) //보내는 대상
		
		message.subject = "MovieThree 인증번호가 도착했습니다." //제목
		
		var msgg = ""
		msgg += "<div style='margin:100px;'>"
		msgg += "<h1> 안녕하세요 MoviThree 인증 메일 입니다.</h1>"
		msgg += "<br>"
		msgg += "<p>아래 코드를 메일 인증 창으로 돌아가 입력해주세요<p>"
		msgg += "<br>"
		msgg += "<p>감사합니다!<p>"
		msgg += "<br>"
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>"
		msgg += "<h3 style='color:blue;'>메일 인증 코드입니다.</h3>"
		msgg += "<div style='font-size:130%'>"
		msgg += "CODE : <strong>"
		msgg += pass.toString() + "</strong><div><br/> "
		msgg += "</div>"
		message.setText(msgg, "utf-8", "html") //내용
		
		message.setFrom(InternetAddress("moviethree0415@naver.com", "MovieThree"))
		return message
	}
	
	override fun sendMessage(to: String, pass: String?) {
		emailSender.send(createMessage(to, pass))
	}
}

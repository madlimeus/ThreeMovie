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
class ChangePasswordEmailServiceImpl(
	val emailSender: JavaMailSender,
) : EmailService {
	override fun createMessage(to: String, pass: String?): MimeMessage {
		val message = emailSender.createMimeMessage()
		
		message.addRecipients(Message.RecipientType.TO, to) //보내는 대상
		
		message.subject = "MovieThree 고객님 계정의 비밀번호가 변경되었습니다." //제목
		
		var msgg = ""
		msgg += "<div style='margin:100px;'>"
		msgg += "<h1> 안녕하세요 MoviThree 비밀번호 변경 안내 메일 입니다.</h1>"
		msgg += "<br>"
		msgg += "<p>비밀번호가 변경되셔서 알려드립니다.<br>본인이 아니시라면 이메일 비밀번호와 사이트 비밀번호를 둘 다 변경하시길 권장드립니다.<p>"
		msgg += "<br>"
		msgg += "<p>감사합니다!<p>"
		msgg += "<br>"
		msgg += "</div>"
		message.setText(msgg, "utf-8", "html") //내용
		
		message.setFrom(InternetAddress("moviethree0415@naver.com", "MovieThree"))
		return message
	}
	
	override fun sendMessage(to: String, pass: String?) {
		emailSender.send(createMessage(to, pass))
	}
}

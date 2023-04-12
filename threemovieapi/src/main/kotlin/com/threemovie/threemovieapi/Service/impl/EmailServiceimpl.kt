package com.threemovie.threemovieapi.Service.impl

import com.threemovie.threemovieapi.Entity.UserSignUpAuth
import com.threemovie.threemovieapi.Repository.UserLoginRepository
import com.threemovie.threemovieapi.Repository.UserSignUpAuthRepository
import com.threemovie.threemovieapi.Service.EmailService
import com.threemovie.threemovieapi.Utils.RandomKeyString
import jakarta.mail.Message
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@EnableAutoConfiguration
class EmailServiceimpl(
	val emailSender: JavaMailSender,
	val userSignUpAuthRepository: UserSignUpAuthRepository,
	val userLoginRepository: UserLoginRepository,
) : EmailService {
	
	fun createSignUpMessage(to: String, ePw: String): MimeMessage {
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
		msgg += ePw + "</strong><div><br/> "
		msgg += "</div>"
		message.setText(msgg, "utf-8", "html") //내용
		
		message.setFrom(InternetAddress("moviethree0415@naver.com", "MovieThree"))
		return message
	}
	
	fun createPassowrdResetMessage(to: String, ePw: String): MimeMessage {
		val message = emailSender.createMimeMessage()
		
		message.addRecipients(Message.RecipientType.TO, to) //보내는 대상
		
		message.subject = "MovieThree 임시 비밀번호가 도착했습니다." //제목
		
		var msgg = ""
		msgg += "<div style='margin:100px;'>"
		msgg += "<h1> 안녕하세요 MoviThree 비밀번호 초기화 메일 입니다.</h1>"
		msgg += "<br>"
		msgg += "<p>아래 코드로 로그인 하신 뒤에 비밀번호를 꼭 변경해주세요.<p>"
		msgg += "<br>"
		msgg += "<p>감사합니다!<p>"
		msgg += "<br>"
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>"
		msgg += "<h3 style='color:blue;'>임시 비밀번호입니다.</h3>"
		msgg += "<div style='font-size:130%'>"
		msgg += "CODE : <strong>"
		msgg += ePw + "</strong><div><br/> "
		msgg += "</div>"
		message.setText(msgg, "utf-8", "html") //내용
		
		message.setFrom(InternetAddress("moviethree0415@naver.com", "MovieThree"))
		return message
	}
	
	fun createPassowrdChangeMessage(to: String): MimeMessage {
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
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>"
		msgg += "<div style='font-size:130%'>"
		msgg += "</div>"
		message.setText(msgg, "utf-8", "html") //내용
		
		message.setFrom(InternetAddress("moviethree0415@naver.com", "MovieThree"))
		return message
	}
	
	fun sendAuthMail(to: String) {
		val ePw = RandomKeyString.randomAlphabetNumber(8)
		sendMessage(to, "auth", ePw)
		
		val date = LocalDateTime.now().plusMinutes(5)
		val userSignUpAuth = UserSignUpAuth(to, ePw, date, false)
		userSignUpAuthRepository.save(userSignUpAuth)
	}
	
	override fun sendMessage(to: String, messageType: String, ePw: String) {
		var message: MimeMessage
		when (messageType) {
			"auth" -> message = createSignUpMessage(to, ePw)
			"resetpass" -> message = createPassowrdResetMessage(to, ePw)
			"changepass" -> message = createPassowrdChangeMessage(to)
			else -> throw IllegalArgumentException()
		}
		
		try {
			emailSender.send(message)
		} catch (es: MailException) {
			es.printStackTrace()
			throw IllegalArgumentException()
		}
	}
}

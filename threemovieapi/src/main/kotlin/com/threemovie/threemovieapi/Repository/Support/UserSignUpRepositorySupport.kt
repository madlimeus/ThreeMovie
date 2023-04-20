package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUserSignUpAuth
import com.threemovie.threemovieapi.Entity.UserSignUpAuth
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserSignUpRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(UserSignUpAuth::class.java) {
	val userSignUp: QUserSignUpAuth = QUserSignUpAuth.userSignUpAuth
	
	fun checkAuthCode(email: String, authCode: String): Boolean {
		val date = LocalDateTime.now().minusMinutes(5)
		return query
			.selectFrom(userSignUp)
			.where(userSignUp.userEmail.eq(email), userSignUp.authCode.eq(authCode), userSignUp.expiredDate.goe(date))
			.fetchOne() != null
	}
	
	@Transactional
	fun updateAuthSuccess(email: String) {
		query.update(userSignUp)
			.set(userSignUp.authSuccess, true)
			.where(userSignUp.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun deletePrevAuth(email: String) {
		query.delete(userSignUp)
			.where(userSignUp.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	fun existsSuccessCode(email: String): Boolean {
		return query
			.select(userSignUp.userEmail)
			.from(userSignUp)
			.where(
				userSignUp.userEmail.eq(email),
				userSignUp.authSuccess.eq(true),
			)
			.fetchOne() != null
	}
}

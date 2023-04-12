package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUserSignUpAuth
import com.threemovie.threemovieapi.Entity.UserSignUpAuth
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserSignUpRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(UserSignUpAuth::class.java) {
	val userSignUp: QUserSignUpAuth = QUserSignUpAuth.userSignUpAuth
	
	fun existsSignCode(email: String): Boolean {
		val dateCondition = LocalDateTime.now()
		
		return query
			.select(userSignUp.userEmail)
			.from(userSignUp)
			.where(userSignUp.userEmail.eq(email), userSignUp.expiredDate.goe(dateCondition))
			.fetchFirst() != null
	}
	
	fun existsSuccessCode(email: String): Boolean {
		val dateCondition = LocalDateTime.now().minusMinutes(30)
		
		return query
			.select(userSignUp.userEmail)
			.from(userSignUp)
			.where(
				userSignUp.userEmail.eq(email),
				userSignUp.authSuccess.eq(true),
				userSignUp.expiredDate.goe(dateCondition)
			)
			.fetchFirst() != null
	}
}

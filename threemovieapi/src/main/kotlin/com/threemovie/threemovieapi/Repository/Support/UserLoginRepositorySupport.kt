package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUserLogin
import com.threemovie.threemovieapi.Entity.UserLogin
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserLoginRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(UserLogin::class.java) {
	val userLogin: QUserLogin = QUserLogin.userLogin
	
	fun existsEmail(email: String): Boolean {
		return query
			.select(userLogin.userEmail)
			.from(userLogin)
			.where(userLogin.userEmail.eq(email))
			.fetchFirst() != null
	}
	
	fun updatePass(email: String, pass: String): Unit {
		query.update(userLogin)
			.set(userLogin.userPassword, pass)
			.where(userLogin.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
}

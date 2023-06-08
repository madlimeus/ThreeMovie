package com.threemovie.threemovieapi.domain.user.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.user.entity.domain.QUserLogin
import com.threemovie.threemovieapi.domain.user.entity.domain.UserLogin
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserLoginRepositorySupport(
	val query: JPAQueryFactory,
	val passwordEncoder: BCryptPasswordEncoder
) : QuerydslRepositorySupport(UserLogin::class.java) {
	val userLogin: QUserLogin = QUserLogin.userLogin
	
	fun existsEmail(email: String): Boolean {
		return query
			.select(userLogin.email)
			.from(userLogin)
			.where(userLogin.email.eq(email))
			.fetchOne() != null
	}
	
	fun getUserLoginByEmail(email: String): UserLogin? {
		return query
			.selectFrom(userLogin)
			.where(userLogin.email.eq(email))
			.fetchOne()
	}
	
	@Transactional
	fun updatePass(email: String, pass: String) {
		query.update(userLogin)
			.set(userLogin.password, pass)
			.where(userLogin.email.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun deleteUserLogin(email: String) {
		query.delete(userLogin)
			.where(userLogin.email.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
}

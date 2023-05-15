package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUserLogin
import com.threemovie.threemovieapi.Entity.UserLogin
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
			.select(userLogin.userEmail)
			.from(userLogin)
			.where(userLogin.userEmail.eq(email))
			.fetchOne() != null
	}
	
	fun getUserLoginByEmail(email: String): UserLogin? {
		return query
			.selectFrom(userLogin)
			.where(userLogin.userEmail.eq(email))
			.fetchOne()
	}
	
	@Transactional
	fun updatePass(email: String, pass: String) {
		query.update(userLogin)
			.set(userLogin.userPassword, pass)
			.where(userLogin.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun deleteUserLogin(email: String) {
		query.delete(userLogin)
			.where(userLogin.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
}

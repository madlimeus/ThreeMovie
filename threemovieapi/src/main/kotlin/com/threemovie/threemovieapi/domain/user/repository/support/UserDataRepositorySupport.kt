package com.threemovie.threemovieapi.domain.user.repository.support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUserData
import com.threemovie.threemovieapi.Entity.QUserData.userData
import com.threemovie.threemovieapi.domain.user.entity.domain.UserData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class UserDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(userData::class.java) {
	val userData: QUserData = QUserData.userData
	
	fun existsNickName(nickName: String): Boolean {
		return query
			.select(userData)
			.from(userData)
			.where(userData.userNickName.eq(nickName))
			.fetchOne() != null
	}
	
	fun existsEmail(email: String): Boolean {
		return query
			.select(userData)
			.from(userData)
			.where(userData.userEmail.eq(email))
			.fetchOne() != null
	}
	
	@Transactional
	fun updateNickName(email: String, nickName: String) {
		query.update(userData)
			.set(userData.userNickName, nickName)
			.where(userData.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun updateUserData(email: String, nickName: String, sex: Boolean?, birth: LocalDate?) {
		query.update(userData)
			.set(userData.userNickName, nickName)
			.set(userData.userSex, sex)
			.set(userData.userBirth, birth)
			.where(userData.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun deleteUserData(email: String) {
		query.delete(userData)
			.where(userData.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	fun getNickName(email: String): String? {
		return query
			.select(userData.userNickName)
			.from(userData)
			.where(userData.userEmail.eq(email))
			.fetchOne()
	}
	
	fun getUserData(email: String): UserData? {
		return query
			.selectFrom(userData)
			.where(userData.userEmail.eq(email))
			.fetchOne()
	}
}

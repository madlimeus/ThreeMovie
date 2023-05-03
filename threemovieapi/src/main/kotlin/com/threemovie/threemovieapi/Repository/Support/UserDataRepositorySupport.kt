package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.DTO.Request.UpdateUserDataRequest
import com.threemovie.threemovieapi.Entity.QUserData
import com.threemovie.threemovieapi.Entity.QUserData.userData
import com.threemovie.threemovieapi.Entity.UserData
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

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
	fun updateUserData(userRequest: UpdateUserDataRequest) {
		query.update(userData)
			.set(userData.userNickName, userRequest.nickName)
			.set(userData.userSex, userRequest.sex)
			.set(userData.userBirth, userRequest.birth)
			.where(userData.userEmail.eq(userRequest.email))
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
	
	fun getNickName(email: String): String {
		return query
			.select(userData.userNickName)
			.from(userData)
			.where(userData.userEmail.eq(email))
			.fetchFirst()
	}
	
	fun getUserData(email: String): UserData {
		return query
			.selectFrom(userData)
			.where(userData.userEmail.eq(email))
			.fetchFirst()
	}
}

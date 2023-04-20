package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.DTO.Request.UpdateUserInfoRequest
import com.threemovie.threemovieapi.Entity.QUserInfo
import com.threemovie.threemovieapi.Entity.UserInfo
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class UserInfoRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(UserInfo::class.java) {
	val userInfo: QUserInfo = QUserInfo.userInfo
	
	fun existsNickName(nickName: String): Boolean {
		return query
			.select(userInfo)
			.from(userInfo)
			.where(userInfo.userNickName.eq(nickName))
			.fetchOne() != null
	}
	
	@Transactional
	fun updateNickName(email: String, nickName: String) {
		query.update(userInfo)
			.set(userInfo.userNickName, nickName)
			.where(userInfo.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun updateUserInfo(userRequest: UpdateUserInfoRequest) {
		query.update(userInfo)
			.set(userInfo.userNickName, userRequest.nickName)
			.set(userInfo.userSex, userRequest.sex)
			.set(userInfo.userBirth, userRequest.birth)
			.where(userInfo.userEmail.eq(userRequest.email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun deleteUserInfo(email: String) {
		query.delete(userInfo)
			.where(userInfo.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	fun getNickName(email: String): String {
		return query
			.select(userInfo.userNickName)
			.from(userInfo)
			.where(userInfo.userEmail.eq(email))
			.fetchFirst()
	}
	
	fun getUserInfo(email: String): UserInfo {
		return query
			.selectFrom(userInfo)
			.where(userInfo.userEmail.eq(email))
			.fetchFirst()
	}
}

package com.threemovie.threemovieapi.Repository.Support

import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.Entity.QUserInfo
import com.threemovie.threemovieapi.Entity.UserInfo
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
			.fetchFirst() != null
	}
	
	fun updateNickName(email: String, nickName: String): Unit {
		query.update(QUserInfo.userInfo)
			.set(userInfo.userNickName, nickName)
			.where(userInfo.userEmail.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
}

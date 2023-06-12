package com.threemovie.threemovieapi.domain.user.repository.support

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.threemovie.threemovieapi.domain.user.entity.domain.QUserData
import com.threemovie.threemovieapi.domain.user.entity.domain.QUserData.userData
import com.threemovie.threemovieapi.domain.user.entity.domain.QUserLogin.userLogin
import com.threemovie.threemovieapi.domain.user.entity.dto.UserDataDTO
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
class UserDataRepositorySupport(
	val query: JPAQueryFactory
) : QuerydslRepositorySupport(userData::class.java) {
	val userData: QUserData = QUserData.userData
	
	fun existsNickName(nickName: String): Boolean {
		return query
			.select(userData)
			.from(userData)
			.where(userData.nickName.eq(nickName))
			.fetchOne() != null
	}
	
	@Transactional
	fun updateNickName(email: String, nickName: String) {
		query.update(userData)
			.set(userData.nickName, nickName)
			.where(userData.userLogin.email.eq(email))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun updateUserData(id: UUID, nickName: String, sex: Boolean?, birth: LocalDate?) {
		query.update(userData)
			.set(userData.nickName, nickName)
			.set(userData.sex, sex)
			.set(userData.birth, birth)
			.where(userData.id.eq(id))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	@Transactional
	fun deleteUserData(id: UUID) {
		query.delete(userData)
			.where(userData.id.eq(id))
			.execute()
		
		entityManager?.clear()
		entityManager?.flush()
	}
	
	fun getNickName(email: String): String? {
		return query
			.select(userData.nickName)
			.from(userData)
			.join(userData.userLogin, userLogin)
			.where(userLogin.email.eq(email))
			.fetchOne()
	}
	
	fun getUserData(email: String): UserDataDTO? {
		return query
			.select(
				Projections.constructor(
					UserDataDTO::class.java,
					userLogin.email,
					userData.nickName,
					userData.sex,
					userData.birth,
					userData.categories,
					userData.brch
				)
			)
			.from(userData)
			.leftJoin(userData.userLogin, userLogin)
			.where(userLogin.email.eq(email))
			.fetchOne()
	}
}

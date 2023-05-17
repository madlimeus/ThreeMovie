package com.threemovie.threemovieapi.global.service

import org.springframework.stereotype.Component

@Component
class RandomKeyString {
	fun randomAlphabetNumber(wishLength: Int): String {
		
		val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
		
		return (1..wishLength)
			.map { kotlin.random.Random.nextInt(0, charPool.size) }
			.map(charPool::get)
			.joinToString("")
	}
}

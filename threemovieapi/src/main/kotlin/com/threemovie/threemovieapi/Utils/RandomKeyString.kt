package com.threemovie.threemovieapi.Utils

class RandomKeyString {
	companion object {
		fun randomAlphabetNumber(wishLength: Int): String {
			
			val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
			
			return (1..wishLength)
				.map { kotlin.random.Random.nextInt(0, charPool.size) }
				.map(charPool::get)
				.joinToString("")
		}
	}
}

package com.threemovie.threemovieapi.service

import com.threemovie.threemovieapi.Entity.ShowTime

interface ShowTimeService {
	fun getCGV(): List<ShowTime>
}

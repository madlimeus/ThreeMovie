package com.threemovie.threemovieapi.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

@Configuration
class ScheduleConfig : AsyncConfigurer, SchedulingConfigurer {
	fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
		val scheduler = ThreadPoolTaskScheduler()
		scheduler.poolSize = Runtime.getRuntime().availableProcessors() * 2
		scheduler.setThreadNamePrefix("MY-SCHEDULER-")
		scheduler.initialize()

		return scheduler
	}
	
	override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
		taskRegistrar.setTaskScheduler(this.threadPoolTaskScheduler())
	}
}

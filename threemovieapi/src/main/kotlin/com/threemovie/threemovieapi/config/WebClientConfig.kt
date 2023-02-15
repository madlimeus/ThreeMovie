package com.threemovie.threemovieapi.config

import com.sun.jndi.toolkit.url.Uri
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {
	@Bean
	fun webClient(): WebClient = WebClient.builder()
		.clientConnector(
			ReactorClientHttpConnector(
				HttpClient.from(
				TcpClient
			.create()
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
			.doOnConnected { connection: Connection ->
				connection.addHandlerLast(ReadTimeoutHandler(10000, TimeUnit.MILLISECONDS))
				connection.addHandlerLast(WriteTimeoutHandler(10000, TimeUnit.MILLISECONDS))
			}))
		)
		.defaultHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
		.build();
}

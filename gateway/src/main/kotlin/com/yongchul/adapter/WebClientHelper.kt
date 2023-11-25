package com.yongchul.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import com.yongchul.service.helper.ClientHelper
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import java.net.URI
import java.util.*

@Service
class WebClientHelper(
    private val webClient: WebClient
): ClientHelper {
    private val logger = LoggerFactory.getLogger(javaClass)

    class Timer private constructor(val id: String, private val started: Long) {
        companion object {
            fun started() = Timer(UUID.randomUUID().toString(), System.currentTimeMillis())
        }

        fun elapsed() = System.currentTimeMillis() - started
    }

    private fun Any?.toJson() = this?.let { ObjectMapper().writeValueAsString(it) } ?: ""

    private fun Map<String, String>.headerString() = this.map { (k, v) -> "$k=$v" }.joinToString(", ")

    private fun <R> executeGet(
        uri: URI,
        headers: Map<String, String>,
        ptr: ParameterizedTypeReference<R>
    ): R {
        return this.executeGetAsync(uri, headers, ptr).block()!!
    }

    private fun <R> executeGetAsync(
        uri: URI,
        headers: Map<String, String> = mapOf(),
        ptr: ParameterizedTypeReference<R>
    ): Mono<R> {
        val timer = Timer.started()
        logger.info("call server[${timer.id}] GET $uri headers: ${headers.toJson()}")

        val exchange = webClient.get().uri(uri)
        headers.forEach { (k, v) -> exchange.header(k, v) }

        return exchange.retrieve().bodyToMono(ptr).map {
            logger.info("call server[${timer.id}] GET $uri ${timer.elapsed()}")
            it
        }.onErrorResume(WebClientResponseException::class.java) {
            logger.error(
                "failed to call server[${timer.id}] GET $uri, Headers: [${headers.headerString()}], elapsed: ${timer.elapsed()}, results: ${it.responseBodyAsString}",
                it
            )
            Mono.error(it)
        }
    }

    override fun <T> get(uri: URI, headers: Map<String, String>, ptr: ParameterizedTypeReference<T>): T =
        this.executeGet(uri, headers, ptr)
}

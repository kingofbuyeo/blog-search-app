package com.yongchul.service.helper

import org.springframework.core.ParameterizedTypeReference
import java.net.URI

interface ClientHelper {
    fun <T> get(uri: URI, headers: Map<String, String> = mapOf(), ptr: ParameterizedTypeReference<T>): T
}
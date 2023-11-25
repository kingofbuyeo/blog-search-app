package com.yongchul.keyword.util

import com.yongchul.keyword.res.SearchPopularKeywordResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.searchPopularKeyword(): SearchPopularKeywordResponse {
    return this.get().uri("v1/search/keywords")
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(object : ParameterizedTypeReference<SearchPopularKeywordResponse>() {})
        .returnResult().responseBody!!
}
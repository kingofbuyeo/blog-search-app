package com.yongchul.blog.util

import com.yongchul.blog.req.SearchBlogReq
import com.yongchul.blog.res.BlogSearchResponse
import org.springframework.core.ParameterizedTypeReference
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.searchBlog(req: SearchBlogReq): BlogSearchResponse {
    return this.get().uri("v1/search/blog${req.queryParam()}")
        .exchange()
        .expectStatus().is2xxSuccessful
        .expectBody(object : ParameterizedTypeReference<BlogSearchResponse>() {})
        .returnResult().responseBody!!
}
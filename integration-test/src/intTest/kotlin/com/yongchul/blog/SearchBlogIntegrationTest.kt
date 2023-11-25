package com.yongchul.blog

import com.yongchul.blog.req.SearchBlogReq
import com.yongchul.blog.util.searchBlog
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchBlogIntegrationTest(
    private val testClient: WebTestClient
) : FunSpec({

    test("블로그 검색 API 정상 검색 테스트") {
        val result = testClient.searchBlog(SearchBlogReq("꽃"))

        result.items.size shouldBe 50
    }

    test("블로그 검색 API 페이지 및 페이지 사이즈 음수 입력시 정상 조회 테스트"){
        val result = testClient.searchBlog(SearchBlogReq("영화", -10, -100))

        result.items.size shouldBe 50
        result.hasNext shouldBe true
    }
}) {
    override fun extensions() = listOf(SpringExtension)
}
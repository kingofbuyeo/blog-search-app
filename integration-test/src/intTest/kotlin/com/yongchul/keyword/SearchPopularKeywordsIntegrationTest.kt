package com.yongchul.keyword

import com.yongchul.keyword.util.PopularKeywordDataCreator
import com.yongchul.keyword.util.searchPopularKeyword
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchPopularKeywordsIntegrationTest(
    private val testClient: WebTestClient,
    private val searchKeywordDataCreator: PopularKeywordDataCreator
):FunSpec({

    beforeTest {
        searchKeywordDataCreator.creator()
    }

    test("인기 검색어 리스트 조회 API 테스트") {
        val result = testClient.searchPopularKeyword()

        result.hasNext shouldBe true
        result.items shouldHaveSize 10
    }

}) {
    override fun extensions() = listOf(SpringExtension)
}
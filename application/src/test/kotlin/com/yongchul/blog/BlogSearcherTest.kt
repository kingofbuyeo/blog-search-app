package com.yongchul.blog

import com.yongchul.adapter.BlogSearcherFinderImpl
import com.yongchul.adapter.searcher.KakaoBlogSearcher
import com.yongchul.adapter.searcher.NaverBlogSearcher
import com.yongchul.service.searcher.BlogSearcher
import com.yongchul.service.searcher.BlogSearcherFinder
import com.yongchul.service.searcher.OuterBlogSearchService
import com.yongchul.usecase.blog.command.BlogSearchCommand
import com.yongchul.usecase.blog.command.SearchOrdering
import com.yongchul.util.TestWebClientHelper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogSearcherTest(
    @Autowired
    @Qualifier("testWebClientHelper")
    private val testWebClientHelper: TestWebClientHelper,
    @Autowired @Qualifier("naverBlogSearcher") private val naverBlogSearcher: NaverBlogSearcher,
    @Value("\${openApi.kakao.url}") private val url: String,
    @Value("\${openApi.kakao.apiKey}") private val apiKey: String,
) {
    lateinit var kakaoBlogSearcher: BlogSearcher
    lateinit var outerBlogSearchService: OuterBlogSearchService
    lateinit var blogSearcherFinder: BlogSearcherFinder

    @BeforeAll
    fun before() {
        kakaoBlogSearcher = KakaoBlogSearcher(testWebClientHelper, url, apiKey)
        blogSearcherFinder = BlogSearcherFinderImpl(kakaoBlogSearcher, naverBlogSearcher)
        outerBlogSearchService = OuterBlogSearchService(blogSearcherFinder)
    }

    @Test
    fun `블로그 검색 정상 수행`() {
        val result = outerBlogSearchService
            .searchTo(BlogSearchCommand("영화", 1, 50, SearchOrdering.ACCURACY))

        assert(result.items.isNotEmpty())
    }

    @Test
    fun `블로그 검색 도중 에러 발생시 네이버 검색으로 정상 수행`() {
        outerBlogSearchService
            .searchTo(BlogSearchCommand("영화", 1, 50, SearchOrdering.ACCURACY))
        outerBlogSearchService
            .searchTo(BlogSearchCommand("영화", 2, 50, SearchOrdering.ACCURACY))
        val result = assertDoesNotThrow { outerBlogSearchService
            .searchTo(BlogSearchCommand("영화", 3, 50, SearchOrdering.ACCURACY)) }


        assert(result.items.isNotEmpty())
    }
}
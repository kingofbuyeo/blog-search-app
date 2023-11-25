package com.yongchul.keyword

import com.yongchul.usecase.blog.SearchBlogWithPageable
import com.yongchul.usecase.blog.command.BlogSearchCommand
import com.yongchul.usecase.keyword.SearchPopularKeywords
import com.yongchul.usecase.keyword.presenter.PopularKeywordResult
import com.yongchul.util.PopularSearchKeywordDataCreator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.random.Random

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeywordSearchTest(
    @Autowired
    private val popularSearchKeywordDataCreator: PopularSearchKeywordDataCreator,
    @Autowired
    private val searchPopularKeywords: SearchPopularKeywords,
    @Autowired
    private val searchBlogWithPageable: SearchBlogWithPageable
) {
    lateinit var savedKeywords: List<String>
    @BeforeAll
    fun before() {
        val result = popularSearchKeywordDataCreator.create()
        savedKeywords = result.map { it.keyword }
    }

    @Test
    fun `인기 검색어 검색 횟수순 정상 조회 테스트`() {
        val result = searchPopularKeywords.searchPopularKeywords()

        Assertions.assertTrue(result.items.isNotEmpty())
        Assertions.assertTrue(result.hasNext)
        val isKeywordByPopular = checkKeywordByPopular(result)
        Assertions.assertTrue(isKeywordByPopular)
    }

    private fun checkKeywordByPopular(result: PopularKeywordResult): Boolean {
        val rs = result.items
        var isDesc = true
        rs.forEachIndexed { index, it->
            if(index + 1 == rs.size) return@forEachIndexed
            if(it.count < rs[index + 1].count){
                isDesc = false
            }
        }
        return isDesc
    }

    @Test
    fun `신규 인기 검색어 생성 정상 조회`() {
        val newKeyword = createNewKeyword()

        val result = searchPopularKeywords.searchPopularKeywords()

        val keywords = result.items
        Assertions.assertTrue(keywords.any { it.keyword == newKeyword })
    }

    @Test
    fun `미존재 검색어 테스트`() {
        val nonExistKeyword = "미존재"

        val result = searchPopularKeywords.searchPopularKeywords()

        val keywords = result.items
        Assertions.assertFalse(keywords.any{ it.keyword == nonExistKeyword})
    }



    private fun createNewKeyword(): String {
        val newKeyword = "신규"
        (Random.nextInt(5, 10)..Random.nextInt(10,20)).forEach { _ ->
            searchBlogWithPageable.searchBlog(BlogSearchCommand(newKeyword))
        }
        return newKeyword
    }
}
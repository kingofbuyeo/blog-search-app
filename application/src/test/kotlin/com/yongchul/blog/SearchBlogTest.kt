package com.yongchul.blog

import com.yongchul.blog.repository.PopularSearchKeywordRepository
import com.yongchul.usecase.blog.SearchBlogWithPageable
import com.yongchul.usecase.blog.command.BlogSearchCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.Executors

@SpringBootTest
class SearchBlogTest(
    @Autowired
    private val searchBlogWithPageable: SearchBlogWithPageable,
    @Autowired
    private val popularSearchKeywordRepository: PopularSearchKeywordRepository
) {

    @Test
    fun `블로그 검색어 데이터 정상 생성`() {
        val command = BlogSearchCommand("겨울")
        val result = searchBlogWithPageable.searchBlog(command)
        val keyword = popularSearchKeywordRepository.findByKeyword(command.query)


        Assertions.assertTrue(result.items.isNotEmpty())
        Assertions.assertNotNull(keyword)
    }

    @Test
    fun `블로그 검색시 Race Condition 상황 정상 처리` (){
        val query = "한국"
        doExecuteSearch(query)

        val keyword = popularSearchKeywordRepository.findByKeyword(query)


        Assertions.assertNotNull(keyword)
        Assertions.assertEquals(keyword?.count, 3)
    }

    private fun doExecuteSearch(query: String) {
        val thread = Executors.newFixedThreadPool(3)
        val first = thread.submit { searchBlogWithPageable.searchBlog(BlogSearchCommand(query, page = 1)) }
        val second = thread.submit { searchBlogWithPageable.searchBlog(BlogSearchCommand(query, page = 2)) }
        val third = thread.submit { searchBlogWithPageable.searchBlog(BlogSearchCommand(query, page = 3)) }
        first.get()
        second.get()
        third.get()
    }
}
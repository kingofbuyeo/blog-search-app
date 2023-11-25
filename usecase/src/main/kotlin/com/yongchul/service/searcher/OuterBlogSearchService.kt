package com.yongchul.service.searcher

import com.yongchul.usecase.blog.presenter.BlogSearchResult
import com.yongchul.usecase.blog.command.BlogSearchCommand
import org.springframework.stereotype.Service

@Service
class OuterBlogSearchService(
    private val blogSearcherFinder: BlogSearcherFinder
) {
    fun searchTo(command: BlogSearchCommand): BlogSearchResult {

        return try {
            val searcher = blogSearcherFinder.findSearcher(SearcherType.KAKAO)
            searcher.search(command)
        } catch (e: Exception) {
            managedSearchError(command)
        }
    }

    private fun managedSearchError(
        command: BlogSearchCommand
    ): BlogSearchResult {
        val searcher = blogSearcherFinder.findSearcher(SearcherType.NAVER)
        return try {
            searcher.search(command)
        } catch (e: Exception) {
            BlogSearchResult.empty()
        }
    }

}
package com.yongchul.usecase.blog

import com.yongchul.blog.repository.PopularSearchKeywordRepository
import com.yongchul.service.searcher.OuterBlogSearchService
import com.yongchul.usecase.blog.presenter.BlogSearchResult
import com.yongchul.usecase.blog.command.BlogSearchCommand
import com.yongchul.usecase.keyword.factory.KeywordFactory
import jakarta.persistence.OptimisticLockException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchBlogWithPageable(
    private val outerBlogSearchService: OuterBlogSearchService,
    private val popularSearchKeywordRepository: PopularSearchKeywordRepository,
    private val keywordFactory: KeywordFactory
) {
    @Retryable(
        value = [ObjectOptimisticLockingFailureException::class, DataIntegrityViolationException::class, OptimisticLockException::class],
        maxAttempts = 3,
        backoff = Backoff(delay = 1000)
    )
    @Transactional
    fun searchBlog(command: BlogSearchCommand): BlogSearchResult{
        val result = outerBlogSearchService.searchTo(command)
        // Event sourcing 으로 변경 하면 사용자 안정성 향상
        popularKeyword(command)
        return result
    }

    private fun popularKeyword(command: BlogSearchCommand) {
        val keyword =
            popularSearchKeywordRepository.findByKeyword(command.query) ?: keywordFactory.create(command.query)
        keyword.searchOneMore()
        popularSearchKeywordRepository.save(keyword)
    }
}
package com.yongchul.usecase.keyword

import com.yongchul.blog.repository.PopularSearchKeywordRepository
import com.yongchul.usecase.keyword.factory.PopularKeywordPresenterFactory
import com.yongchul.usecase.keyword.presenter.PopularKeywordResult
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchPopularKeywords(
    private val popularSearchKeywordRepository: PopularSearchKeywordRepository,
    private val factory: PopularKeywordPresenterFactory
) {

    @Transactional(readOnly = true)
    fun searchPopularKeywords(): PopularKeywordResult{
        val result = popularSearchKeywordRepository.popularKeywords(PageRequest.of(0, 10))
        return factory.toPresenter(result)
    }
}
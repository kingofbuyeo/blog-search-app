package com.yongchul.usecase.keyword.factory

import com.yongchul.blog.domain.PopularSearchKeyword
import com.yongchul.usecase.keyword.presenter.PopularKeywordInfo
import com.yongchul.usecase.keyword.presenter.PopularKeywordResult
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Component

@Component
class PopularKeywordPresenterFactory {

    fun toPresenter(keywords: Slice<PopularSearchKeyword>): PopularKeywordResult{
        return PopularKeywordResult(keywords.hasNext(), keywords.content.map {
            PopularKeywordInfo(it.keyword, it.count)
        })
    }
}
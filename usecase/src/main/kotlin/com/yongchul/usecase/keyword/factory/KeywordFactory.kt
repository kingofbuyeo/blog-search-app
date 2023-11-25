package com.yongchul.usecase.keyword.factory

import com.yongchul.blog.domain.PopularSearchKeyword
import org.springframework.stereotype.Component

@Component
class KeywordFactory {

    fun create(query: String): PopularSearchKeyword{
        return PopularSearchKeyword(query, 0)
    }
}
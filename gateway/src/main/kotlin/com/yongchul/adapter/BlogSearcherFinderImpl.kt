package com.yongchul.adapter

import com.yongchul.service.searcher.BlogSearcher
import com.yongchul.service.searcher.BlogSearcherFinder
import com.yongchul.service.searcher.SearcherType
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class BlogSearcherFinderImpl(
    @Qualifier("kakaoBlogSearcher") private val kakaoBlogSearcher: BlogSearcher,
    @Qualifier("naverBlogSearcher") private val naverBlogSearcher: BlogSearcher,
): BlogSearcherFinder {
    override fun findSearcher(type: SearcherType): BlogSearcher {
        return when(type){
            SearcherType.NAVER -> naverBlogSearcher
            else -> kakaoBlogSearcher
        }
    }
}
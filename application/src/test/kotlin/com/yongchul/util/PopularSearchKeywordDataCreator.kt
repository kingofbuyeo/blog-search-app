package com.yongchul.util

import com.yongchul.blog.domain.PopularSearchKeyword
import com.yongchul.blog.repository.PopularSearchKeywordRepository
import com.yongchul.usecase.keyword.factory.KeywordFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
class PopularSearchKeywordDataCreator(
    private val popularSearchKeywordRepository: PopularSearchKeywordRepository,
    private val keywordFactory: KeywordFactory
) {

    @Transactional
    fun create(): List<PopularSearchKeyword>{
        val keywords = listOf("꽃", "하늘", "여름", "영화", "낙엽", "노래", "kotlin", "spring", "springboot", "multi module", "합격").map {
            val keyword = keywordFactory.create(it)
            keyword.count = Random.nextLong(10)
            keyword
        }

        popularSearchKeywordRepository.saveAll(keywords)
        return popularSearchKeywordRepository.popularKeywords(PageRequest.of(0, 10)).content
    }
}
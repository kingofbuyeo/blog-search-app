package com.yongchul.keyword.util

import com.yongchul.blog.repository.PopularSearchKeywordRepository
import com.yongchul.usecase.keyword.factory.KeywordFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@Service
class PopularKeywordDataCreator(
    private val popularSearchKeywordRepository: PopularSearchKeywordRepository,
    private val keywordFactory: KeywordFactory
) {

    @Transactional
    fun creator(){
        val keywords = listOf("java", "자바", "DDD", "멀티모듈", "spring boot kotlin", "헥사고날", "클린아키텍쳐", "sprigboot kotlin", "고양이", "면접", "기술 블로그", "머신러닝").map {
            val keyword = keywordFactory.create(it)
            keyword.count = Random.nextLong(10)
            keyword
        }

        popularSearchKeywordRepository.saveAll(keywords)
    }
}
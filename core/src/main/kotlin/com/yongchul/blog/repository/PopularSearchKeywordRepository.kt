package com.yongchul.blog.repository

import com.yongchul.blog.domain.PopularSearchKeyword
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PopularSearchKeywordRepository: JpaRepository<PopularSearchKeyword, Long> {
    fun findByKeyword(keyword: String): PopularSearchKeyword?

    @Query("SELECT p FROM PopularSearchKeyword p ORDER BY p.count DESC, p.keyword ASC ")
    fun popularKeywords(pageable: Pageable): Slice<PopularSearchKeyword>
}
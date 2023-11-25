package com.yongchul.adapter.searcher.dto

import com.yongchul.usecase.blog.presenter.BlogInfo
import com.yongchul.usecase.blog.presenter.BlogSearchResult
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class NaverBlogResultDTO(
    val total: Long,
    val display: Long,
    val start: Long,
    val items: List<NaverBlogInfoDTO>
){
    private fun hasNext(): Boolean{
        return total - (start * display) > 0
    }

    fun toCommonResult(): BlogSearchResult{
        return BlogSearchResult(this.total, hasNext(), this.items.map {
            BlogInfo(it.title, it.description, it.link, it.bloggername, it.toDateTime())
        })
    }
}

data class NaverBlogInfoDTO(
    val title: String,
    val link: String,
    val description: String,
    val bloggername: String,
    val postdate: String
){
    fun toDateTime(): LocalDateTime{
        val ld = LocalDate.parse(this.postdate, DateTimeFormatter.ofPattern("yyyyMMdd"))
        return ld.atStartOfDay()
    }
}
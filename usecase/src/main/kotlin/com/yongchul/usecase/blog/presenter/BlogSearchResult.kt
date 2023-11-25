package com.yongchul.usecase.blog.presenter

import java.time.LocalDateTime

data class BlogSearchResult(
    val totalCount: Long,
    val hasNext: Boolean,
    val items: List<BlogInfo>
){
    companion object{
        fun empty(): BlogSearchResult{
            return BlogSearchResult(0, false, emptyList())
        }
    }
}

data class BlogInfo(
    val title: String,
    val contents: String,
    val link: String,
    val blogName: String,
    val createdAt: LocalDateTime
)

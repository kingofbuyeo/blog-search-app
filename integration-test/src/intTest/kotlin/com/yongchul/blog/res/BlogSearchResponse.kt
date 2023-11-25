package com.yongchul.blog.res

import java.time.LocalDateTime

data class BlogSearchResponse(
    val totalCount: Long,
    val hasNext: Boolean,
    val items: List<BlogInfoResponse>
)

data class BlogInfoResponse(
    val title: String,
    val contents: String,
    val link: String,
    val blogName: String,
    val createdAt: LocalDateTime
)
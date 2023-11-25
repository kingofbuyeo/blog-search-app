package com.yongchul.keyword.res

data class SearchPopularKeywordResponse(
    val hasNext: Boolean,
    val items: List<PopularKeywordInfo>
)

data class PopularKeywordInfo(
    val keyword: String,
    val count: Long
)

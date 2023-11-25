package com.yongchul.usecase.keyword.presenter

data class PopularKeywordResult(
    val hasNext: Boolean,
    val items: List<PopularKeywordInfo>
){
    fun keywords(): List<String>{
        return this.items.map { it.keyword }
    }
}

data class PopularKeywordInfo(
    val keyword: String,
    val count: Long
)

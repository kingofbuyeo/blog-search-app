package com.yongchul.blog.req

data class SearchBlogReq(
    val query: String,
    val page: Int = 1,
    val pageSize: Int = 50,
    val ordering: SearchOrdering = SearchOrdering.ACCURACY
){
    fun queryParam(): String{
        return "?query=$query&page=$page&pageSize=$pageSize&ordering=${ordering.name}"
    }
}

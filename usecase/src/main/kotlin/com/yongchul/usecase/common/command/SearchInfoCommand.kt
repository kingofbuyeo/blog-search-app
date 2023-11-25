package com.yongchul.usecase.common.command

open class SearchInfoCommand(
    query: String,
    page: Int,
    pageSize: Int
){
    open val query: String = query.ifBlank {
        throw IllegalArgumentException("Query must be not null")
    }

    open val page: Int = when(page < 0){
        true -> 1
        else -> page
    }
    open val pageSize: Int = when(pageSize < 0){
        true -> 50
        else -> pageSize
    }
}

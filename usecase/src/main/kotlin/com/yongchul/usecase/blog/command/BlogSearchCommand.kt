package com.yongchul.usecase.blog.command

import com.yongchul.usecase.common.command.SearchInfoCommand

class BlogSearchCommand(
    query: String,
    page: Int = 1,
    pageSize: Int = 50,
    val ordering: SearchOrdering = SearchOrdering.ACCURACY
): SearchInfoCommand(query, page, pageSize){
    fun naverSortOrdering(): String{
        return this.ordering.naverOrdering()
    }

    fun kakaoSortOrdering(): String{
        return this.ordering.kakaoOrdering()
    }

    fun pageStr(): String{
        return this.page.toString()
    }

    fun pageSizeStr(): String{
        return this.pageSize.toString()
    }
}

package com.yongchul.adapter.searcher

import com.yongchul.usecase.blog.command.BlogSearchCommand
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.net.URLEncoder

fun BlogSearchCommand.toKakaoBlogSearchReq(): MultiValueMap<String, String> {
    val params = LinkedMultiValueMap<String, String>()
    params["query"] = this.encodedQuery()
    params["page"] = this.pageStr()
    params["size"] = this.pageSizeStr()
    params["sort"] = this.kakaoSortOrdering()
    return params
}

fun BlogSearchCommand.toNaverBlogSearchReq(): MultiValueMap<String, String> {
    val params = LinkedMultiValueMap<String, String>()
    params["query"] = this.encodedQuery()
    params["display"] = this.pageSizeStr()
    params["start"] = this.pageStr()
    params["sort"] = this.naverSortOrdering()
    return params
}

fun BlogSearchCommand.encodedQuery(): String{
    return URLEncoder.encode(this.query, "UTF-8")
}
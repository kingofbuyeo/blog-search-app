package com.yongchul.adapter.searcher

import com.yongchul.adapter.WebClientHelper
import com.yongchul.adapter.searcher.dto.NaverBlogResultDTO
import com.yongchul.service.searcher.BlogSearcher
import com.yongchul.usecase.blog.presenter.BlogSearchResult
import com.yongchul.usecase.blog.command.BlogSearchCommand
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class NaverBlogSearcher(
    private val webClientHelper: WebClientHelper,
    @Value("\${openApi.naver.url}") private val url: String,
    @Value("\${openApi.naver.apiKey}") private val apiKey: String,
    @Value("\${openApi.naver.secret}") private val secret: String
) : BlogSearcher {


    override fun search(command: BlogSearchCommand): BlogSearchResult {
        val headers = mapOf("X-Naver-Client-Id" to apiKey, "X-Naver-Client-Secret" to secret)
        val uri = UriComponentsBuilder.fromUriString(url).queryParams(command.toNaverBlogSearchReq()).build().toUri()
        return try {
            val rs = webClientHelper.get(uri, headers, object : ParameterizedTypeReference<NaverBlogResultDTO>() {})
            rs.toCommonResult()
        }catch (e: Exception){
            BlogSearchResult.empty()
        }
    }

}
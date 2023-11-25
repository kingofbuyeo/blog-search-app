package com.yongchul.adapter.searcher

import com.yongchul.adapter.searcher.dto.KakaoBlogResultDTO
import com.yongchul.service.helper.ClientHelper
import com.yongchul.service.searcher.BlogSearcher
import com.yongchul.usecase.blog.presenter.BlogSearchResult
import com.yongchul.usecase.blog.command.BlogSearchCommand
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class KakaoBlogSearcher(
    private val webClientHelper: ClientHelper,
    @Value("\${openApi.kakao.url}") private val url: String,
    @Value("\${openApi.kakao.apiKey}") private val apiKey: String
) : BlogSearcher {

    override fun search(command: BlogSearchCommand): BlogSearchResult {
        val header = mapOf("Authorization" to "KakaoAK $apiKey")
        val uri = UriComponentsBuilder.fromUriString(url).queryParams(command.toKakaoBlogSearchReq()).build().toUri()
        val result = webClientHelper.get(uri, header, object : ParameterizedTypeReference<KakaoBlogResultDTO>() {})
        return result.toCommonResult()
    }
}
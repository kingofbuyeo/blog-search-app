package com.yongchul.controller.keyword

import com.yongchul.usecase.keyword.SearchPopularKeywords
import com.yongchul.usecase.keyword.presenter.PopularKeywordResult
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인기검색어 조회 컨트롤러")
@RestController
@RequestMapping("v1/search/keywords")
class PopularSearchKeywordController(
    private val searchPopularKeywords: SearchPopularKeywords
) {

    @Operation(summary = "인기 검색어 조회 API", description = "최근까지 검색한 블로그 검색어 인기순 Top 10 조회용 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "success"),
            ApiResponse(responseCode = "500", description = "5000000: 서버 에러가 발생 하였습니다. 담당자에게 문의 해주세요.")
        ]
    )
    @GetMapping
    fun searchPopularKeywords(): PopularKeywordResult{
        return searchPopularKeywords.searchPopularKeywords()
    }
}
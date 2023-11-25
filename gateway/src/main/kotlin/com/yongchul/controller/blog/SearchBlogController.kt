package com.yongchul.controller.blog

import com.yongchul.usecase.blog.presenter.BlogSearchResult
import com.yongchul.usecase.blog.SearchBlogWithPageable
import com.yongchul.usecase.blog.command.BlogSearchCommand
import com.yongchul.usecase.blog.command.SearchOrdering
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "블로그 검색 컨트롤러")
@RestController
@RequestMapping("v1/search/blog")
class SearchBlogController(
    private val searchBlogWithPageable: SearchBlogWithPageable
) {

    @Operation(summary = "블로그 검색 API", description = "블로그 검색 API 기본적으로 다음 블로그 검색을 수행 \n " +
            "다음 블로그가 장애가 발생시 네이버 블로그 리스트 검색 수행 \n ")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "success"),
            ApiResponse(responseCode = "400", description = "4000000: 요청 파마리터가 잘못 되었습니다."),
            ApiResponse(responseCode = "500", description = "5000000: 서버 에러가 발생 하였습니다. 담당자에게 문의 해주세요.")
        ]
    )
    @GetMapping
    fun searchBlog(
        @RequestParam(name = "query") query: String,
        @RequestParam(name = "page", defaultValue = "1") page: Int,
        @RequestParam(name = "pageSize", defaultValue = "50") pageSize: Int,
        @RequestParam(name = "ordering", defaultValue = "ACCURACY") ordering: SearchOrdering
    ): BlogSearchResult{
        return searchBlogWithPageable.searchBlog(BlogSearchCommand(query, page, pageSize, ordering))
    }
}
package com.yongchul.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(info = Info(title = "검색 오픈 API를 활용한 어플리케이션", version = "1.0"))
@Configuration
class SwaggerConfig {

    @Bean
    fun blogApiGroup(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("블로그 검색 관련").pathsToMatch(
                "/v1/search/blog/**"
            )
            .build()

    @Bean
    fun keywordApiGroup(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("인기 검색어 관련").pathsToMatch(
                "/v1/search/keywords/**"
            )
            .build()
}
package com.yongchul.adapter.searcher.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.yongchul.usecase.blog.presenter.BlogInfo
import com.yongchul.usecase.blog.presenter.BlogSearchResult
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class KakaoBlogResultDTO(
    val meta: KakaoBlogMetaDTO,
    val documents: List<KakaoBlogInfoDTO>
) {
    fun toCommonResult(): BlogSearchResult {
        return BlogSearchResult(this.meta.totalCount, this.meta.hasNext(), this.documents.map {
            BlogInfo(
                it.title, it.contents, it.url, it.blogname, it.toDateTime()
            )
        })
    }
}

data class KakaoBlogMetaDTO(
    @JsonAlias("total_count")
    val totalCount: Long,
    @JsonAlias("pageable_count")
    val pageableCount: Long,
    @JsonAlias("is_end")
    val isEnd: Boolean
){
    fun hasNext(): Boolean{
        return !this.isEnd
    }
}

data class KakaoBlogInfoDTO(
    val title: String,
    val contents: String,
    val url: String,
    val blogname: String,
    val datetime: String
) {
    fun toDateTime(): LocalDateTime {
        return LocalDateTime.from(
            Instant.from(DateTimeFormatter.ISO_DATE_TIME.parse(this.datetime)).atZone(ZoneId.of("Asia/Seoul"))
        )
    }
}

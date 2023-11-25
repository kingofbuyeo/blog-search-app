package com.yongchul.blog.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "popular_search_keyword",
    uniqueConstraints = [
        UniqueConstraint(name = "popularSearchKeywordUniqueIndex_001", columnNames = ["keyword"])
    ]
)
class PopularSearchKeyword(
    val keyword: String,
    var count: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val no: Long = 0L

    @Version
    val version: Long = 0L

    val createdAt: LocalDateTime = LocalDateTime.now()

    fun searchOneMore(){
        this.count ++
    }
}
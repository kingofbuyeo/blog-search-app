package com.yongchul.service.searcher

interface BlogSearcherFinder {
    fun findSearcher(type: SearcherType): BlogSearcher
}
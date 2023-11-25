package com.yongchul.service.searcher

import com.yongchul.usecase.blog.presenter.BlogSearchResult
import com.yongchul.usecase.blog.command.BlogSearchCommand

interface BlogSearcher {
    fun search(command: BlogSearchCommand): BlogSearchResult
}
package com.yongchul.usecase.blog.command

enum class SearchOrdering {
    RECENT {
        override fun naverOrdering(): String {
            return "date"
        }

        override fun kakaoOrdering(): String {
            return "recency"
        }
    }, ACCURACY {
        override fun naverOrdering(): String {
            return "sim"
        }

        override fun kakaoOrdering(): String {
            return "accuracy"
        }
    };

    abstract fun naverOrdering(): String
    abstract fun kakaoOrdering(): String
}
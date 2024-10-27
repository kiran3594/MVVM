package com.example.mvvm.utils

import com.example.mvvm.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = "https://newsapi.org/"
    const val SEARCH_NEWS_DELAY_TIME=500L
    const val QUERY_PAGE_SIZE=20
}
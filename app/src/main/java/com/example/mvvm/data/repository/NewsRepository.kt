package com.example.mvvm.data.repository

import com.example.mvvm.data.api.RetrofitInstance
import com.example.mvvm.data.db.ArticleDatabase

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingResult(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(search = searchQuery, pageNumber = pageNumber)
}
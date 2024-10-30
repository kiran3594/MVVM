package com.example.mvvm.data.repository

import com.example.mvvm.data.api.RetrofitInstance
import com.example.mvvm.data.db.ArticleDatabase
import com.example.mvvm.data.models.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(val db: ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingResult(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun getSearchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(search = searchQuery, pageNumber = pageNumber)

    suspend fun upsertArticle(article: Article) = db.articleDoa().upsert(article)

    fun getSavedArticles() = db.articleDoa().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.articleDoa().deleteArticle(article)
}
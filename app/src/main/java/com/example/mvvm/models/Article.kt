package com.example.mvvm.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
    (
    tableName = "Articles"
)
data class Article(
    @PrimaryKey
    val id:Int?=null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
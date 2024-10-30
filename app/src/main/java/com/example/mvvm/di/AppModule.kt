package com.example.mvvm.di

import android.content.Context
import com.example.mvvm.data.db.ArticleDatabase
import com.example.mvvm.data.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ArticleDatabase {
        return ArticleDatabase(context)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(db: ArticleDatabase): NewsRepository {
        return NewsRepository(db)
    }
}
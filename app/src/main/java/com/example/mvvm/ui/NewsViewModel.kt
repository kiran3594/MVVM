package com.example.mvvm.ui

import androidx.lifecycle.ViewModel
import com.example.mvvm.data.NewsRepository

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {
}
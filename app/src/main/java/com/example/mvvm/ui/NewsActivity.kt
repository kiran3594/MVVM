package com.example.mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm.R
import com.example.mvvm.data.NewsRepository
import com.example.mvvm.databinding.ActivityNewsBinding

import com.example.mvvm.db.ArticleDatabase

class NewsActivity : AppCompatActivity() {

    private val binding: ActivityNewsBinding by lazy { ActivityNewsBinding.inflate(layoutInflater) }
    lateinit var viewModel: NewsViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val articleDatabase = ArticleDatabase(applicationContext)
        val newsRepository = NewsRepository(articleDatabase)
        val viewModelProviderFactory = NewViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(
            this, viewModelProviderFactory
        ).get(NewsViewModel::class.java)

        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup Bottom Navigation with NavController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
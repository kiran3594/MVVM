package com.example.mvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mvvm.R
import com.example.mvvm.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    private val binding: ActivityNewsBinding by lazy { ActivityNewsBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setup Bottom Navigation with NavController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
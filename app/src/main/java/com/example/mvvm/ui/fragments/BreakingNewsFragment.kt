package com.example.mvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.R
import com.example.mvvm.ui.NewsViewModel

class BreakingNewsFragment:Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize ViewModel scoped to the Activity (shared between fragments and activity)
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class.java]
    }
}
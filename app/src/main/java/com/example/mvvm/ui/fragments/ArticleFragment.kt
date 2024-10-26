package com.example.mvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mvvm.databinding.FragmentArticleBinding
import com.example.mvvm.ui.activity.NewsActivity
import com.example.mvvm.ui.viewmodels.NewsViewModel

class ArticleFragment : Fragment() {

    lateinit var viewModel: NewsViewModel

    lateinit var binding: FragmentArticleBinding

    val args: ArticleFragmentArgs by navArgs()

    val TAG="ArticleFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize ViewModel scoped to the Activity (shared between fragments and activity)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            Log.d(TAG,"kiran :${article.url}")
            loadUrl(article.url)
        }
    }

}
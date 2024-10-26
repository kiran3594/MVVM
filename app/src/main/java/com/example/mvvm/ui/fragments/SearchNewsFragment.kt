package com.example.mvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.databinding.FragmentSearchNewsBinding
import com.example.mvvm.ui.activity.NewsActivity
import com.example.mvvm.ui.adapters.NewsAdapter
import com.example.mvvm.ui.viewmodels.NewsViewModel
import com.example.mvvm.utils.Constants.SEARCH_NEWS_DELAY_TIME
import com.example.mvvm.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var searchAdapter: NewsAdapter

    lateinit var binding: FragmentSearchNewsBinding

    private val TAG = "SearchNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {

            viewModel = (activity as NewsActivity).viewModel

            setUpRecyclerView()

            searchAdapter.setOnClickListener {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                findNavController().navigate(
                    R.id.action_searchNewsFragment_to_articleFragment,
                    bundle
                )
            }

            var job: Job? = null

            binding.etSearch.addTextChangedListener { editable ->
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_NEWS_DELAY_TIME)
                    editable?.let {
                        if (it.toString().isNotEmpty()) {
                            viewModel.getSearchingNews(it.toString())
                        }
                    }

                }
            }

            viewModel.searchNews.observe(
                viewLifecycleOwner
            ) { result ->
                when (result) {
                    is Resource.Success -> {
                        hideProgressBar()
                        result.data?.let { newsResponse ->
                            Log.d(TAG, "Kiran : Success : ${newsResponse.articles}")
                            searchAdapter.differ.submitList(newsResponse.articles)
                        }
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "Error : ${result.message}")
                        hideProgressBar()
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }
                }

            }
        } catch (e: Exception) {
            Log.d(tag, "Kiran :Exception $e")
        }
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun setUpRecyclerView() {
        searchAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }
}
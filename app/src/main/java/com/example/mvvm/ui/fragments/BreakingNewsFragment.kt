package com.example.mvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.adapters.NewsAdapter
import com.example.mvvm.databinding.FragmentBreakingNewsBinding
import com.example.mvvm.ui.NewsActivity
import com.example.mvvm.ui.NewsViewModel
import com.example.mvvm.utils.Resource

class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private val TAG = BreakingNewsFragment::class.java.name

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            // Initialize the ViewBinding for this fragment
            // Initialize ViewModel scoped to the Activity
            viewModel = (activity as NewsActivity).viewModel


            // Set up RecyclerView
            setUpRecyclerView()
            viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let { newsResponse ->
                            newsAdapter.differ.submitList(newsResponse.articles)
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        response.message?.let { errorMessage ->
                            Log.d(TAG, "An error occured:$errorMessage")
                        }
                    }

                    is Resource.Loading -> showProgressBar()
                }
            })
        } catch (e: Exception) {
            Log.d(tag, "Exception $e")
        }

    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE

    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter()

        // Use binding to access the RecyclerView
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}


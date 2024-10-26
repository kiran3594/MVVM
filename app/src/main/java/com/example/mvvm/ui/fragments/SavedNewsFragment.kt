package com.example.mvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.ui.adapters.NewsAdapter
import com.example.mvvm.databinding.FragmentSavedNewsBinding
import com.example.mvvm.ui.activity.NewsActivity
import com.example.mvvm.ui.viewmodels.NewsViewModel

class SavedNewsFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    lateinit var binding: FragmentSavedNewsBinding
    lateinit var savedAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialize ViewModel scoped to the Activity (shared between fragments and activity)
        viewModel = (activity as NewsActivity).viewModel

        setUpRecyclerView()
        savedAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

    }

    private fun setUpRecyclerView() {
        savedAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = savedAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}
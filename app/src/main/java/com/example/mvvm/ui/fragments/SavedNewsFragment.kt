package com.example.mvvm.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.ui.adapters.NewsAdapter
import com.example.mvvm.databinding.FragmentSavedNewsBinding
import com.example.mvvm.ui.activity.NewsActivity
import com.example.mvvm.ui.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

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

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer { articlesList->
            articlesList.let {
                savedAdapter.differ.submitList(it)
            }
        })

        val itemTouchHelperCallback = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position=viewHolder.adapterPosition
                val article=savedAdapter.differ.currentList[position]
                viewModel.deleteArticle(article = article)
                Snackbar.make(view,"Deleted Article Successfully",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveArticleIntoDatabase(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
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
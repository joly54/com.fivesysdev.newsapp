package com.fivesysdev.newsapp

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.databinding.FragmentNewsListBinding
import com.fivesysdev.newsapp.room.DataBaseViewModel
import com.fivesysdev.newsapp.room.models.news.news.News
import com.fivesysdev.newsapp.viewModel.topHeadlines.NewListViewModel
import org.koin.android.ext.android.inject

class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    private lateinit var recyclerView: RecyclerView
    private val apiService: ApiService by inject()
    private lateinit var binding: FragmentNewsListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsListBinding.bind(view)
        recyclerView = binding.recyclerNewsList
        initializeRecyclerView()
        initPadding()
    }

    private fun initializeRecyclerView() {
        val adapter = NewsAdapter()
        val vm = NewListViewModel(apiService, requireActivity().application)

        val dbViewModel = DataBaseViewModel(requireActivity().application)
        val observer = Observer<List<News>> {
                adapter.setTopHeadlines(it)
        }
        dbViewModel.news.getAll().observe(viewLifecycleOwner) {
            adapter.setTopHeadlines(it)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
    private fun initPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.recyclerNewsList) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

package com.fivesysdev.newsapp

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.databinding.FragmentNewsListBinding
import com.fivesysdev.newsapp.room.DataBaseViewModel
import com.fivesysdev.newsapp.viewModel.topHeadlines.NewListViewModel
import org.koin.android.ext.android.inject

open class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    protected lateinit var recyclerView: RecyclerView
    protected lateinit var binding: FragmentNewsListBinding
    protected val viewModel: NewListViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsListBinding.bind(view)
        recyclerView = binding.recyclerNewsList
        initializeRecyclerView()
        initPadding()
    }

    protected open fun initializeRecyclerView() {
        val adapter = NewsAdapter()
        viewModel.getTopHeadlines()

        val dbViewModel = DataBaseViewModel(requireActivity().application)
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

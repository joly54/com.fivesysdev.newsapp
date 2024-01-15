package com.fivesysdev.newsapp

import androidx.recyclerview.widget.LinearLayoutManager
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.room.DataBaseViewModel

class FavoriteFragment: NewsListFragment() {
    override fun initializeRecyclerView() {
        val adapter = NewsAdapter()

        val dbViewModel = DataBaseViewModel(requireActivity().application)
        dbViewModel.news.getAllFavorite().observe(viewLifecycleOwner) {
            adapter.setTopHeadlines(it)
        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}
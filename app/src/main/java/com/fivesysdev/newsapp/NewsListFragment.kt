package com.fivesysdev.newsapp

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.databinding.FragmentNewsListBinding
import com.fivesysdev.newsapp.viewModel.topHeadlines.TopHeadlinesViewModel
import org.koin.android.ext.android.inject

class NewsListFragment : Fragment(R.layout.fragment_news_list) {
    private lateinit var recyclerView: RecyclerView
    private val apiService: ApiService by inject()
    private lateinit var binding: FragmentNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsListBinding.bind(view)
        recyclerView = binding.recyclerNewsList
        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        val adapter = NewsAdapter()
        val vm = TopHeadlinesViewModel(apiService, adapter)

        vm.state.observe(viewLifecycleOwner, vm.stateObserver)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }
}

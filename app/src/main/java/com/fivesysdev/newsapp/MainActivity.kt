package com.fivesysdev.newsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fivesysdev.newsapp.adapters.NewsAdapter
import com.fivesysdev.newsapp.data.ApiService
import com.fivesysdev.newsapp.viewModel.topHeadlines.TopHeadlinesViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private val apiService: ApiService by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerMovieList)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerMovieList)
        val apiService = apiService
        val adapter = NewsAdapter(this)
        val vm = TopHeadlinesViewModel(apiService, adapter)

        vm.state.observe(this, vm.stateObserver)


        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
    }
}
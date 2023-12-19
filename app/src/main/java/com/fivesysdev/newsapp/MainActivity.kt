package com.fivesysdev.newsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fivesysdev.newsapp.adapters.ListAdapter
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val apiService = apiService
        val vm = TopHeadlinesViewModel(apiService)

        recyclerView = findViewById(R.id.recycler)

        vm.state.observe(this, vm.stateObserver)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = ListAdapter(vm.ModelList)

        recyclerView.adapter = adapter

    }
}
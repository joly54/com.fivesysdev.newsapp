package com.fivesysdev.newsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fivesysdev.newsapp.common.presentation.viewBinding
import com.fivesysdev.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        setupNavigation()


        binding.bottomNavigationView.setupWithNavController(navController = NavController(this))
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.newsListFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.newsListFragment)
                    true
                }

                R.id.favoriteFragment -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.favoriteFragment)
                    true
                }

                else -> {
                    true
                }
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.newsListFragment)
    }
}
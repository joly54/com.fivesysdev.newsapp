package com.fivesysdev.newsapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.fivesysdev.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupNavigation()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener  {
            println("Menu item clicked: ${it.title}")
            when (it.itemId) {
                R.id.item_1 -> {
                    showToast("Item 1 clicked")
                    true
                }

                R.id.item_2 -> {
                    showToast("Item 2 clicked")
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun setupNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.newsListFragment)
    }
}
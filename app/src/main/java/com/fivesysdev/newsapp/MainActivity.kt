package com.fivesysdev.newsapp

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.fivesysdev.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupNavigation()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.bottomNavigation.setOnItemReselectedListener{
            println("id ${it.itemId} selected")
            when(it.itemId){
                R.id.item_1 -> {
                    findNavController(this, R.id.nav_host_fragment).navigate(R.id.newsListFragment)
                }
                R.id.item_2 -> {
                    findNavController(this, R.id.nav_host_fragment).navigate(R.id.favoriteFragment)
                }
            }

        }
    }
    private fun setupNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.newsListFragment)
    }
}
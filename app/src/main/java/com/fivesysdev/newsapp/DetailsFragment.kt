package com.fivesysdev.newsapp

import android.R.attr.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import coil.load
import com.fivesysdev.newsapp.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeBinding(inflater.inflate(R.layout.fragment_details, container, false))

        val arguments = arguments
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val imageUrl = arguments?.getString("image_url")
        println("Image URL: $imageUrl")

        binding.textViewTitle.text = title
        binding.textViewOverview.text = description
        binding.imageView.load(imageUrl)

        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentDetails) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        return binding.root
    }
    private fun initializeBinding(view: View) {
        binding = FragmentDetailsBinding.bind(view)
    }
}
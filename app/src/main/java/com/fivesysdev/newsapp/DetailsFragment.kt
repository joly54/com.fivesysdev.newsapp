package com.fivesysdev.newsapp

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import coil.load
import com.fivesysdev.newsapp.databinding.FragmentDetailsBinding
import com.fivesysdev.newsapp.room.DataBaseViewModel
import com.fivesysdev.newsapp.room.models.news.favorite.FavoriteNews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var dbViewModel: DataBaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializeBinding(inflater.inflate(R.layout.fragment_details, container, false))
        initPadding()
        initShareButton()
        initOpenAtSiteButton()
        initBackButton()
        initViewModel()
        initFavoriteButton(binding)

        val arguments = arguments
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")
        val imageUrl = arguments?.getString("image_url")

        binding.textViewTitle.text = title
        binding.textViewOverview.text = description
        binding.imageView.load(imageUrl)

        return binding.root
    }
    private fun initFavoriteButton(binding: FragmentDetailsBinding){
        val button = binding.faviroteButton
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val news_id = arguments?.getInt("id") ?: 0
            val isFavorite = dbViewModel.favoriteNews.isFavorite(news_id)
            println("News is favorite: $news_id $isFavorite")
            if (isFavorite) {
                button.setImageDrawable(resources.getDrawable(R.drawable.ic_star, null))
            } else {
                button.setImageDrawable(resources.getDrawable(R.drawable.ic_star_outline, null))
            }
        }
        button.setOnClickListener {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                val news_id = arguments?.getInt("id") ?: 0
                val isFavorite = dbViewModel.favoriteNews.isFavorite(news_id)
                println("News is favorite: $news_id $isFavorite")
                if (isFavorite) {
                    dbViewModel.favoriteNews.unFavorite(news_id)
                    button.setImageDrawable(resources.getDrawable(R.drawable.ic_star_outline, null))
                } else {
                    dbViewModel.favoriteNews.upsert(FavoriteNews(news_id = news_id))
                    button.setImageDrawable(resources.getDrawable(R.drawable.ic_star, null))
                }
            }
        }
    }
    private fun initializeBinding(view: View) {
        binding = FragmentDetailsBinding.bind(view)
    }

    private fun initViewModel() {
        dbViewModel = DataBaseViewModel(requireContext().applicationContext as Application)
    }

    private fun initPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.fragmentDetails) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initShareButton(
        title: String = binding.textViewTitle.text.toString(),
        description: String = binding.textViewOverview.text.toString()
    ) {
        binding.shareButton.setOnClickListener {
            val shareIntent = android.content.Intent().apply {
                action = android.content.Intent.ACTION_SEND
                putExtra(android.content.Intent.EXTRA_TEXT, "$title\n\n$description")
                type = "text/plain"
            }
            val shareIntentChooser = android.content.Intent.createChooser(shareIntent, null)
            startActivity(shareIntentChooser)
        }
    }

    private fun initOpenAtSiteButton() {
        binding.buttonOpenAtSite.setOnClickListener {
            val url = arguments?.getString("url")
            if (url == null) return@setOnClickListener
            val openAtSiteIntent = android.content.Intent().apply {
                action = android.content.Intent.ACTION_VIEW
                data = android.net.Uri.parse(url)
            }
            startActivity(openAtSiteIntent)
        }
    }

    private fun initBackButton() {
        binding.backButton.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}
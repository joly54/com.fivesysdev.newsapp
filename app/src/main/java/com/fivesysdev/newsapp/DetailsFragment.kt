package com.fivesysdev.newsapp

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
    val args: DetailsFragmentArgs by navArgs()

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
        initFavoriteButton()

        val title = args.title
        val description = args.description
        val imageUrl = args.imageUrl
        val url = args.url
        val id = args.id

        with(binding) {
            textViewTitle.text = title
            textViewOverview.text = description
            imageView.load(imageUrl)
            return root
        }
    }

    private fun initFavoriteButton() {
        with(binding) {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                val news_id = args.id
                val isFavorite = dbViewModel.favoriteNews.isFavorite(news_id)
                if (isFavorite) {
                    faviroteButton.setImageDrawable(resources.getDrawable(R.drawable.ic_star, null))
                } else {
                    faviroteButton.setImageDrawable(
                        resources.getDrawable(
                            R.drawable.ic_star_outline,
                            null
                        )
                    )
                }
            }
            faviroteButton.setOnClickListener {
                val coroutineScope = CoroutineScope(Dispatchers.IO)
                coroutineScope.launch {
                    val news_id = args.id
                    val isFavorite = dbViewModel.favoriteNews.isFavorite(news_id)
                    println("News is favorite: $news_id $isFavorite")
                    if (isFavorite) {
                        dbViewModel.favoriteNews.unFavorite(news_id)
                        faviroteButton.setImageDrawable(
                            resources.getDrawable(
                                R.drawable.ic_star_outline,
                                null
                            )
                        )
                    } else {
                        dbViewModel.favoriteNews.upsert(FavoriteNews(news_id = news_id))
                        faviroteButton.setImageDrawable(
                            resources.getDrawable(
                                R.drawable.ic_star,
                                null
                            )
                        )
                    }
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

    private fun initShareButton() {
        with(binding) {
            val title = textViewTitle.text.toString()
            val description = textViewOverview.text.toString()
            shareButton.setOnClickListener {
                val shareIntent = android.content.Intent().apply {
                    action = android.content.Intent.ACTION_SEND
                    putExtra(android.content.Intent.EXTRA_TEXT, "$title\n\n$description")
                    type = "text/plain"
                }
                val shareIntentChooser = android.content.Intent.createChooser(shareIntent, null)
                startActivity(shareIntentChooser)
            }
        }
    }

    private fun initOpenAtSiteButton() {
        with(binding) {
            buttonOpenAtSite.setOnClickListener {
                val url = args.url
                if (url == null) return@setOnClickListener
                val openAtSiteIntent = android.content.Intent().apply {
                    action = android.content.Intent.ACTION_VIEW
                    data = android.net.Uri.parse(url)
                }
                startActivity(openAtSiteIntent)
            }
        }
    }

    private fun initBackButton() {
        with(binding) {
            backButton.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}
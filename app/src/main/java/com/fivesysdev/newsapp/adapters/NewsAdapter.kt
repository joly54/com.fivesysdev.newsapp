package com.fivesysdev.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fivesysdev.newsapp.NewsListFragmentDirections
import com.fivesysdev.newsapp.databinding.NewsCardBinding
import com.fivesysdev.newsapp.room.models.news.news.News


class NewsAdapter(
) : RecyclerView.Adapter<NewsAdapter.MyNewsHolder>() {
    private var _TopHeadlines: MutableLiveData<List<News>> = MutableLiveData()


    fun setTopHeadlines(list: List<News>) {
       updateTopHeadlines(list)
    }
    fun updateTopHeadlines(list: List<News>) {
        val diffCallback = TopHeadlinesDiffCallback(_TopHeadlines.value, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        _TopHeadlines.value = list
        diffResult.dispatchUpdatesTo(this)
    }

    fun getTopHeadlines(): List<News>? {
        return _TopHeadlines.value
    }


    class MyNewsHolder(private val binding: NewsCardBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
            private lateinit var item: News

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {


            val action = NewsListFragmentDirections.actionNewsListFragmentToDetailsFragment(
                title = item.title,
                description = item.description,
                url = item.url,
                imageUrl = item.urlToImage,
                id = item.id,

            )
            val navController = Navigation.findNavController(itemView)
            navController.navigate(action)
        }
        fun bind(item: News) {
            this.item = item
            with(binding){
                txtName.text = item.title
                newsImage.load(item.urlToImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsHolder {
        val binding = NewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyNewsHolder(binding)
    }

    override fun getItemCount() = _TopHeadlines.value?.size ?: 0

    override fun onBindViewHolder(holder: MyNewsHolder, position: Int) {
        if (_TopHeadlines.value == null) return
        val listItem = _TopHeadlines.value!![position]
        holder.bind(listItem)
    }
    private class TopHeadlinesDiffCallback(
        private val oldList: List<News>?,
        private val newList: List<News>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList?.size ?: 0

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition)?.url == newList[newItemPosition].url
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList?.get(oldItemPosition) == newList[newItemPosition]
        }
    }
}
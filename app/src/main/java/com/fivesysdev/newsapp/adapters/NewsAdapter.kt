package com.fivesysdev.newsapp.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fivesysdev.newsapp.DetailsFragment
import com.fivesysdev.newsapp.R
import com.fivesysdev.newsapp.databinding.NewsCardBinding
import com.fivesysdev.newsapp.model.topHeadlines.Article
import com.fivesysdev.newsapp.model.topHeadlines.TopHeadlines


class NewsAdapter(
) : RecyclerView.Adapter<NewsAdapter.MyNewsHolder>() {
    private var _TopHeadlines: MutableLiveData<TopHeadlines> = MutableLiveData()


    fun setTopHeadlines(topHeadlines: TopHeadlines) {
       updateTopHeadlines(topHeadlines)
    }
    fun updateTopHeadlines(newTopHeadlines: TopHeadlines) {
        val diffCallback = TopHeadlinesDiffCallback(_TopHeadlines.value?.articles, newTopHeadlines.articles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        _TopHeadlines.value = newTopHeadlines
        diffResult.dispatchUpdatesTo(this)
    }

    fun getTopHeadlines(): TopHeadlines? {
        return _TopHeadlines.value
    }


    class MyNewsHolder(private val binding: NewsCardBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val navController = Navigation.findNavController(itemView)
            val Bundle = Bundle()
            Bundle.putString("title", binding.txtName.text.toString())
            Bundle.putString("description", binding.txtTeam.text.toString())
            Bundle.putString("author", binding.txtCreatedby.text.toString())
            navController.navigate(R.id.action_newsListFragment_to_detailsFragment, Bundle)
        }

        fun bind(item: Article) {
            binding.txtName.text = item.title
            binding.txtTeam.text = item.description
            binding.imageMovie.load(item.urlToImage)
            binding.txtCreatedby.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsHolder {
        val binding = NewsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyNewsHolder(binding)
    }

    override fun getItemCount() = _TopHeadlines.value?.articles?.size ?: 0

    override fun onBindViewHolder(holder: MyNewsHolder, position: Int) {
        if (_TopHeadlines.value == null) return
        val listItem = _TopHeadlines.value!!.articles[position]
        holder.bind(listItem)
    }
    private class TopHeadlinesDiffCallback(
        private val oldList: List<Article>?,
        private val newList: List<Article>
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
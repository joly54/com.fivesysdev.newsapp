package com.fivesysdev.newsapp.adapters

import android.content.Context
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
import com.fivesysdev.newsapp.databinding.FragmentNewsListBinding
import com.fivesysdev.newsapp.databinding.NewsCardBinding
import com.fivesysdev.newsapp.model.topHeadlines.Article
import com.fivesysdev.newsapp.model.topHeadlines.TopHeadlines


class NewsAdapter(
    private val context: Context,
    private val fragmentManager: androidx.fragment.app.FragmentManager,
    private val binding: FragmentNewsListBinding
) : RecyclerView.Adapter<NewsAdapter.MyNewsHolder>() {
    private var _TopHeadlines: MutableLiveData<TopHeadlines> = MutableLiveData()


    fun setTopHeadlines(topHeadlines: TopHeadlines) {
        updateList(topHeadlines)
    }
    fun updateList(newList: TopHeadlines) {
        val diffCallback = NewsDiffCallback(_TopHeadlines.value?.articles ?: emptyList(), newList.articles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        _TopHeadlines.value = newList
        diffResult.dispatchUpdatesTo(this)
    }
    class NewsDiffCallback(
        private val oldList: List<Article>,
        private val newList: List<Article>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].url == newList[newItemPosition].url
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    fun getTopHeadlines(): TopHeadlines? {
        return _TopHeadlines.value
    }


    class MyNewsHolder(
        private val itemView: View,
        private val fragmentManager: androidx.fragment.app.FragmentManager,
        private val binding: NewsCardBinding = NewsCardBinding.bind(itemView)
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = binding.txtName
        val description: TextView = binding.txtTeam
        val image: ImageView = binding.newsImage
        val createdBy: TextView = binding.txtCreatedby

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val transaction = fragmentManager.beginTransaction()
            val secondFragment = DetailsFragment()
            transaction.replace(R.id.fragment_linear_layout, secondFragment)
            transaction.commit()
        }

        fun bind(item: Article) {
            title.text = item.title
            description.text = item.description
            image.load(item.urlToImage)
            createdBy.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.news_card, parent, false)
        itemView.setOnClickListener {
            val navController = Navigation.findNavController(itemView)
            navController.navigate(R.id.action_newsListFragment_to_detailsFragment2)
            println("Clicked")
        }
        return MyNewsHolder(itemView, fragmentManager)
    }

    override fun getItemCount() = _TopHeadlines.value?.articles?.size ?: 0
    override fun onBindViewHolder(holder: MyNewsHolder, position: Int) {
        if (_TopHeadlines.value == null) return
        val listItem = _TopHeadlines.value!!.articles.get(position)
        holder.bind(listItem)
        holder.image.load(listItem.urlToImage)
        holder.title.text = listItem.title
        holder.description.text = listItem.description
    }
}
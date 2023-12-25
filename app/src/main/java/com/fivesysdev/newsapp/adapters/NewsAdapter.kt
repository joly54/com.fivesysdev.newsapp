package com.fivesysdev.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fivesysdev.newsapp.DetailsFragment
import com.fivesysdev.newsapp.R
import com.fivesysdev.newsapp.model.topHeadlines.Article
import com.fivesysdev.newsapp.model.topHeadlines.TopHeadlines


class NewsAdapter(
    private val context: Context,
    private val fragmentManager: androidx.fragment.app.FragmentManager
) : RecyclerView.Adapter<NewsAdapter.MyNewsHolder>() {
    private var _TopHeadlines: MutableLiveData<TopHeadlines> = MutableLiveData()


    fun setTopHeadlines(topHeadlines: TopHeadlines) {
        _TopHeadlines.value = topHeadlines
        println("SetTopHeadlines size: ${topHeadlines.articles.size}")
        notifyDataSetChanged()
    }

    fun getTopHeadlines(): TopHeadlines? {
        return _TopHeadlines.value
    }


    class MyNewsHolder(
        private val itemView: View,
        private val fragmentManager: androidx.fragment.app.FragmentManager
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.txt_name)
        val description: TextView = itemView.findViewById(R.id.txt_team)
        val image: ImageView = itemView.findViewById(R.id.image_movie)
        val createdBy: TextView = itemView.findViewById(R.id.txt_createdby)

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
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
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
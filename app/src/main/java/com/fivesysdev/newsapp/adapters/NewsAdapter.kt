package com.fivesysdev.newsapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fivesysdev.newsapp.R
import com.fivesysdev.newsapp.model.topHeadlines.Article
import com.fivesysdev.newsapp.model.topHeadlines.TopHeadlines


class NewsAdapter(
    private val context: Context,
): RecyclerView.Adapter<NewsAdapter.MyNewsHolder>() {
    private var _TopHeadlines: MutableLiveData<TopHeadlines> = MutableLiveData()

    fun setTopHeadlines(topHeadlines: TopHeadlines) {
        _TopHeadlines.value = topHeadlines
        println("SetTopHeadlines size: ${topHeadlines.articles.size}")
        notifyDataSetChanged()
    }
    fun getTopHeadlines(): TopHeadlines? {
        return _TopHeadlines.value
    }


    class MyNewsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.txt_name)
        val description: TextView = itemView.findViewById(R.id.txt_team)
        val image: ImageView = itemView.findViewById(R.id.image_movie)
        val createdBy: TextView = itemView.findViewById(R.id.txt_createdby)

        fun bind(item: Article) {
            title.text = item.title
            description.text = item.description
            image.load(item.urlToImage)
            createdBy.text = item.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNewsHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyNewsHolder(itemView)
    }
    override fun getItemCount() = _TopHeadlines.value?.articles?.size ?: 0
    override fun onBindViewHolder(holder: MyNewsHolder, position: Int) {
        if(_TopHeadlines.value == null) return
        val listItem = _TopHeadlines.value!!.articles.get(position)
        holder.bind(listItem)

        holder.image.load(listItem.urlToImage)
        holder.title.text = listItem.title
        holder.description.text = listItem.description
    }
}
package com.fivesysdev.newsapp.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fivesysdev.newsapp.R
import com.fivesysdev.newsapp.model.topHeadlines.Article

class ListAdapter(private val modelList: MutableList<Article>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.textView)
        fun bind(item: Article) {
            textView.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.text_item_view, parent))
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = modelList[position]
        holder.bind(item)
    }
}
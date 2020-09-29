package com.example.cryptos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptos.R
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.NewsRecyclerViewCallback
import com.example.cryptos.interfaces.RecyclerViewCallback
import com.example.cryptos.network.model.Article


class NewsListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var news = emptyList<Article>()
    private val context = context
    var recyclerViewCallback: NewsRecyclerViewCallback? = null

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsTitleItemView: TextView = itemView.findViewById(R.id.news_title)
        val newsImgItemView: ImageView = itemView.findViewById(R.id.news_img)
        val newsByView: TextView = itemView.findViewById(R.id.news_by)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemView = inflater.inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = news[position]
        holder.itemView.setOnClickListener {
            this.recyclerViewCallback?.onRecycleViewItemClick(current, position)
        }
        holder.newsTitleItemView.text = current.title
        holder.newsByView.text = "por "+current.author
        Glide
            .with(context)
            .load(current.urlToImage)
            .placeholder(R.color.colorAccent)
            .into(holder.newsImgItemView);



    }

    private fun intoString(
        textReal: String,
        pos: Int
    ): String? {
        val stringBuilder = StringBuilder(textReal)
        stringBuilder.insert(pos, "/")
        return "Precio $stringBuilder"
    }

    internal fun setNews(news: List<Article>) {
        this.news = news
        notifyDataSetChanged()
    }

    override fun getItemCount() = news.size

    fun setOnCallbackListener(recyclerViewCallback: NewsRecyclerViewCallback) {
        this.recyclerViewCallback = recyclerViewCallback
    }

}




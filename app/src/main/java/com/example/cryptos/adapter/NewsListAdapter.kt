package com.example.cryptos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptos.R
import com.example.cryptos.databinding.NewsItemBinding
import com.example.cryptos.network.model.Article


class NewsListAdapter() : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private var items = emptyList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    internal fun setNews(news: List<Article>) {
        this.items = news
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.item = item
            binding.newsImg.load(item.urlToImage){
                placeholder(R.drawable.loading)
            }
            binding.executePendingBindings()
        }
    }
}




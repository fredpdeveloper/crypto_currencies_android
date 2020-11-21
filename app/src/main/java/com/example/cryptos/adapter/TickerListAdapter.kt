package com.example.cryptos.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptos.database.Ticker
import com.example.cryptos.databinding.TickerItemBinding


class TickerListAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Ticker, TickerListAdapter.TickerViewHolder>(
        DiffCallback
    ) {
    class TickerViewHolder(private val binding: TickerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Ticker) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Ticker>() {
        override fun areItemsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Ticker, newItem: Ticker): Boolean {
            return oldItem.tickerId == newItem.tickerId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TickerItemBinding.inflate(inflater,parent,false)
        return TickerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        val product = getItem(position)
        holder.itemView.setOnClickListener {
            Log.e("asdasd","click")
            onClickListener.onClick(product)
        }
        holder.bind(product)
    }

    class OnClickListener(val clickListener: (item: Ticker) -> Unit) {
        fun onClick(item: Ticker) = clickListener(item)
    }
}
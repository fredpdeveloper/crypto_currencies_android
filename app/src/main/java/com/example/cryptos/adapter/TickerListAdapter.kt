package com.example.cryptos.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptos.R
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.RecyclerViewCallback
import kotlinx.coroutines.channels.ticker


class TickerListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<TickerListAdapter.TickerViewHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var tickers = emptyList<Ticker>()
    private var fTickers = emptyList<Ticker>()
    var recyclerViewCallback: RecyclerViewCallback? = null

    inner class TickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tickerNameItemView: TextView = itemView.findViewById(R.id.ticker_name)
        val tickerPriceItemView: TextView = itemView.findViewById(R.id.ticker_price)
        val tickerImgItemView: ImageView = itemView.findViewById(R.id.ticker_img)
        val tickerImgCountryItemView: ImageView = itemView.findViewById(R.id.ticker_img_country)
        val tickerItemView: ConstraintLayout =
            itemView.findViewById<ConstraintLayout>(R.id.ticker_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val itemView = inflater.inflate(R.layout.ticker_item, parent, false)
        return TickerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        val current = fTickers[position]
        holder.itemView.setOnClickListener {
            this.recyclerViewCallback?.onRecycleViewItemClick(current, position)
        }
        holder.tickerNameItemView.text = intoString(current.market, 3)
        when {
            current.market.contains("ETH") -> {
                holder.tickerImgItemView.setImageResource(R.drawable.ethereum)
            }
            current.market.contains("XLM") -> {
                holder.tickerImgItemView.setImageResource(R.drawable.stellar)
            }
            current.market.contains("BTC") -> {
                holder.tickerImgItemView.setImageResource(R.drawable.bitcoin)
            }
            current.market.contains("EOS") -> {
                holder.tickerImgItemView.setImageResource(R.drawable.eos)
            }
        }

        when {
            current.market.contains("CLP") -> {
                holder.tickerPriceItemView.text = "$${current.bid} CLP"
                holder.tickerImgCountryItemView.setImageResource(R.drawable.chile)

            }
            current.market.contains("EUR") -> {
                holder.tickerPriceItemView.text = "€${current.bid} EUR"
                holder.tickerImgCountryItemView.setImageResource(R.drawable.europa)

            }
            current.market.contains("BRL") -> {
                holder.tickerPriceItemView.text = "R$${current.bid} BRL"
                holder.tickerImgCountryItemView.setImageResource(R.drawable.brasil)

            }
            current.market.contains("MXN") -> {
                holder.tickerPriceItemView.text = "$${current.bid} MXN"
                holder.tickerImgCountryItemView.setImageResource(0)
            }
            current.market.contains("ARS") -> {
                holder.tickerPriceItemView.text = "$${current.bid} ARS"
                holder.tickerImgCountryItemView.setImageResource(R.drawable.argentina)

            }
        }


    }

    private fun intoString(
        textReal: String,
        pos: Int
    ): String? {
        val stringBuilder = StringBuilder(textReal)
        stringBuilder.insert(pos, "/")
        return "Precio $stringBuilder"
    }

    internal fun setTickers(tickers: List<Ticker>) {
        this.tickers = tickers
        this.fTickers = tickers
        notifyDataSetChanged()
    }

    override fun getItemCount() = fTickers.size

    fun setOnCallbackListener(recyclerViewCallback: RecyclerViewCallback) {
        this.recyclerViewCallback = recyclerViewCallback
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: Filter.FilterResults
            ) {
                fTickers = filterResults.values as List<Ticker>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()
                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    tickers
                else if (queryString.contains(",")) {
                    tickers.filter {
                        it.market.toLowerCase().contains(queryString)
                    }
                } else {
                    tickers.filter {
                        it.market.toLowerCase().contains(queryString)
                    }
                }

                return filterResults
            }

        }
    }
}




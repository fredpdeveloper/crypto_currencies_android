package com.example.cryptos.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.cryptos.R
import com.example.cryptos.adapter.NewsListAdapter
import com.example.cryptos.adapter.TickerListAdapter
import com.example.cryptos.database.Ticker
import com.example.cryptos.api.model.Article
import com.example.cryptos.api.model.CryptoApiStatus
import com.example.cryptos.api.model.NewsApiStatus
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.mikephil.charting.charts.LineChart
import java.text.DecimalFormat

object BindingAdapterUtils {

    @JvmStatic
    @BindingAdapter("newsListData")
    fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
        val adapter = recyclerView.adapter as NewsListAdapter
        adapter.submitList(data)
    }

    @JvmStatic
    @BindingAdapter("tickersListData")
    fun bindTickerRecyclerView(recyclerView: RecyclerView, data: List<Ticker>?) {
        val adapter = recyclerView.adapter as TickerListAdapter
        adapter.submitList(data)
    }

    @JvmStatic
    @BindingAdapter("countrySrc")
    fun setCountryImg(view: ImageView, country: String) {
        when {
            country.contains("CLP") -> {
                view.setImageResource(R.drawable.chile)

            }
            country.contains("EUR") -> {
                view.setImageResource(R.drawable.europa)

            }
            country.contains("BRL") -> {
                view.setImageResource(R.drawable.brasil)

            }
            country.contains("MXN") -> {
                view.setImageResource(0)
            }
            country.contains("ARS") -> {
                view.setImageResource(R.drawable.argentina)

            }
        }
    }

    @JvmStatic
    @BindingAdapter("tickerSrc")
    fun setTickerImg(view: ImageView, ticker: String) {
        when {
            ticker.contains("ETH") -> {
                view.setImageResource(R.drawable.ethereum)
            }
            ticker.contains("XLM") -> {
                view.setImageResource(R.drawable.stellar)
            }
            ticker.contains("BTC") -> {
                view.setImageResource(R.drawable.bitcoin)
            }
            ticker.contains("EOS") -> {
                view.setImageResource(R.drawable.eos)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "error"])
    fun setImageUrl(view: ImageView, url: String, error: Drawable) {
        view.load(url) {
            view.setImageDrawable(error)
        }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun setIsVisible(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter("tickerPercent", "tickerMarket")
    fun setTickerPercent(view: TextView, percent: Double, market: String) {
        val df = DecimalFormat()
        df.maximumFractionDigits = 2
        view.text = "%${df.format(percent)} $market"
        when {
            percent < 0 -> {
                view.setTextColor(view.context.getColor(R.color.colorRed))
            }
            percent > 0 -> {
                view.setTextColor(view.context.getColor(R.color.colorGreen))
            }
            else -> {
                view.setTextColor(view.context.getColor(R.color.colorBlack))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setLineChartData")
    fun setLineChartData(view: LineChart, tickers: List<Ticker>?) {
        tickers?.let { ChartUtils.renderData(view, it) }

    }

    @JvmStatic
    @BindingAdapter("cryptoApiStatus")
    fun setCryptoApiStatus(view: ShimmerFrameLayout, enum: Enum<CryptoApiStatus>) {

        when (enum) {
            CryptoApiStatus.DONE -> {
                view.visibility = View.GONE
                view.stopShimmerAnimation()
            }
            CryptoApiStatus.ERROR -> {
                view.visibility = View.GONE
                view.stopShimmerAnimation()

            }
            CryptoApiStatus.LOADING -> {
                view.visibility = View.VISIBLE
                view.startShimmerAnimation()

            }
        }

    }

    @JvmStatic
    @BindingAdapter("newsApiStatus")
    fun setNewsApiStatus(view: ShimmerFrameLayout, enum: Enum<NewsApiStatus>) {

        when (enum) {
            CryptoApiStatus.DONE -> {
                view.visibility = View.GONE
                view.stopShimmerAnimation()
            }
            CryptoApiStatus.ERROR -> {
                view.visibility = View.GONE
                view.stopShimmerAnimation()

            }
            CryptoApiStatus.LOADING -> {
                view.visibility = View.VISIBLE
                view.startShimmerAnimation()

            }
        }

    }


}
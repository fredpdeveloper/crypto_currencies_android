package com.example.cryptos.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptos.R
import com.example.cryptos.adapter.TickerListAdapter
import com.example.cryptos.database.Ticker
import com.example.cryptos.interfaces.RecyclerViewCallback
import com.example.cryptos.viewmodel.TickersViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filter.*

class MainActivity : AppCompatActivity(), RecyclerViewCallback {
    private lateinit var tickersViewModel: TickersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = TickerListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter.setOnCallbackListener(this)


        // Get a new or existing ViewModel from the ViewModelProvider.
        tickersViewModel = ViewModelProvider(this).get(TickersViewModel::class.java)
        tickersViewModel.allTickers.observe(this, Observer { tickers ->
            // Update the cached copy of the tickers in the adapter.
            tickers?.let {
                tickersViewModel.insert(it)
                adapter.setTickers(it)
            }
        })
        tickersViewModel.mShowProgressBar.observe(this, Observer { loading ->
            if (loading) {
                recyclerView.visibility = View.GONE
                shimmerFrameLayout.visibility = View.VISIBLE
                shimmerFrameLayout.startShimmerAnimation()
            } else {
                recyclerView.visibility = View.VISIBLE
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmerAnimation()
            }
        })

        chips_group_country.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerFilterChanged(adapter)

            }
        }

        chips_group_exchange.forEach { child ->
            (child as? Chip)?.setOnCheckedChangeListener { _, _ ->
                registerExchangeFilterChanged(adapter)

            }
        }
    }

    /*TODO dejar solo una funcion register*/
    private fun registerFilterChanged(adapter: TickerListAdapter) {
        val ids = chips_group_country.getCheckedChipIds()

        val titles = mutableListOf<CharSequence>()

        ids.forEach { id ->
            titles.add(chips_group_country.findViewById<Chip>(id).text)
        }

        if (titles.isNotEmpty()) {
            adapter.filter.filter(  titles.joinToString(", "))
        } else {
            adapter.filter.filter(null)
        }

    }
    /*TODO dejar solo una funcion register*/
    private fun registerExchangeFilterChanged(adapter: TickerListAdapter) {
        val ids = chips_group_exchange.getCheckedChipIds()

        val titles = mutableListOf<CharSequence>()

        ids.forEach { id ->
            titles.add(chips_group_exchange.findViewById<Chip>(id).text)
        }

        if (titles.isNotEmpty()) {
            adapter.filter.filter(  titles.joinToString(", "))
        } else {
            adapter.filter.filter(null)
        }

    }

    override fun onRecycleViewItemClick(ticker: Ticker, position: Int) {
        tickersViewModel.getTickersByMarker(ticker.market).observe(this, Observer { tickers ->
            // Update the cached copy of the tickers in the adapter.
            tickers?.let {
                val tickerDialog: TickerDialog = TickerDialog(tickers, ticker).newInstance()
                tickerDialog.show(
                    supportFragmentManager,
                    "ticker_dialog"
                )
            }
        })
    }
}